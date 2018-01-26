package uk.co.makosoft.naggingnelly.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.Parcelable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;
import timber.log.Timber;
import uk.co.makosoft.naggingnelly.BoilerplateApplication;
import uk.co.makosoft.naggingnelly.NotLoggedInException;
import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.data.model.Folder;
import uk.co.makosoft.naggingnelly.data.model.Ribot;
import uk.co.makosoft.naggingnelly.util.AndroidComponentUtil;
import uk.co.makosoft.naggingnelly.util.NetworkUtil;
import uk.co.makosoft.naggingnelly.util.RxUtil;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    private Disposable mDisposableLogin;
    private Disposable mDisposableRibot;
    private Disposable mDisposableFolders;
    private Disposable mDisposableActions;
    private Disposable mDisposableConcat;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtil.dispose(mDisposableLogin);
        Observable<String> loginObservable = mDataManager.login("blah", "noooo");
        loginObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableLogin = d;
                    }

                    @Override
                    public void onNext(@NonNull String token) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.w(e, "Error logging in.");
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Logged in successfully!");
                    }
                });

        RxUtil.dispose(mDisposableActions);
        Observable<Action> actionObservable = mDataManager.syncActions();
        actionObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<Action>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableActions = d;
                    }

                    @Override
                    public void onNext(@NonNull Action action) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.w(e, "Error syncing actions.");
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced actions successfully!");
                    }
                });

        RxUtil.dispose(mDisposableRibot);
        Observable<Ribot> ribotObservable = mDataManager.syncRibots();
        ribotObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<Ribot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableRibot = d;
                    }

                    @Override
                    public void onNext(@NonNull Ribot ribot) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.w(e, "Error syncing ribots.");
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced ribots successfully!");
                    }
                });

        RxUtil.dispose(mDisposableFolders);
        Observable<Folder> folderObservable = mDataManager.syncFolders();
        folderObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<Folder>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableFolders = d;
                    }

                    @Override
                    public void onNext(@NonNull Folder folder) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.w(e, "Error syncing folders.");
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced folders successfully!");
                    }
                });

        Observable.concat(actionObservable, ribotObservable, folderObservable).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Parcelable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableConcat = d;
                    }

                    @Override
                    public void onNext(@NonNull Parcelable folder) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mDisposableRibot != null) mDisposableRibot.dispose();
        if (mDisposableFolders != null) mDisposableFolders.dispose();
        if (mDisposableActions != null) mDisposableActions.dispose();
        if (mDisposableConcat != null) mDisposableConcat.dispose();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}
