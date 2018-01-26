package uk.co.makosoft.naggingnelly.ui.login;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.makosoft.naggingnelly.data.DataManager;
import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.data.model.Folder;
import uk.co.makosoft.naggingnelly.data.model.Ribot;
import uk.co.makosoft.naggingnelly.injection.ConfigPersistent;
import uk.co.makosoft.naggingnelly.ui.base.BasePresenter;
import uk.co.makosoft.naggingnelly.ui.main.MainMvpView;
import uk.co.makosoft.naggingnelly.util.RxUtil;

@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void login(String email, String password) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String token) {
                        if (token.length() == 0) {
                            getMvpView().showLoginSuccessful();
                        } else {
                            getMvpView().showLoginSuccessful();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e, "There was an error logging in.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
