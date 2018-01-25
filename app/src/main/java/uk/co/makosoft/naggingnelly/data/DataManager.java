package uk.co.makosoft.naggingnelly.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;
import uk.co.makosoft.naggingnelly.data.local.DatabaseHelper;
import uk.co.makosoft.naggingnelly.data.local.PreferencesHelper;
import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.data.model.Folder;
import uk.co.makosoft.naggingnelly.data.model.LoginDetails;
import uk.co.makosoft.naggingnelly.data.model.Ribot;
import uk.co.makosoft.naggingnelly.data.remote.AccountService;
import uk.co.makosoft.naggingnelly.data.remote.ActionsService;
import uk.co.makosoft.naggingnelly.data.remote.FoldersService;
import uk.co.makosoft.naggingnelly.data.remote.RibotsService;

@Singleton
public class DataManager {

    private final AccountService mAccountService;
    private final RibotsService mRibotsService;
    private final FoldersService mFoldersService;
    private final ActionsService mActionsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(AccountService accountService, RibotsService ribotsService, FoldersService foldersService,
                       ActionsService actionsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mAccountService = accountService;
        mRibotsService = ribotsService;
        mFoldersService = foldersService;
        mActionsService = actionsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Function<List<Ribot>, ObservableSource<? extends Ribot>>() {
                    @Override
                    public ObservableSource<? extends Ribot> apply(@NonNull List<Ribot> ribots)
                            throws Exception {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }


    public Observable<Folder> syncFolders() {
        return mFoldersService.getFolders()
                .concatMap(new Function<List<Folder>, ObservableSource<? extends Folder>>() {
                    @Override
                    public ObservableSource<? extends Folder> apply(@NonNull List<Folder> folders)
                            throws Exception {
                        return mDatabaseHelper.setFolders(folders);
                    }
                });
    }

    public Observable<List<Folder>> getFolders() {
        return mDatabaseHelper.getFolders().distinct();
    }

    public Observable<Action> syncActions() {
        return mActionsService.getActions()
                .concatMap(new Function<List<Action>, ObservableSource<? extends Action>>() {
                    @Override
                    public ObservableSource<? extends Action> apply(@NonNull List<Action> actions)
                            throws Exception {
                        return mDatabaseHelper.setActions(actions);
                    }
                });
    }

    public Observable<List<Action>> getActions() {
        return mDatabaseHelper.getActions().distinct();
    }

    public Observable<Action> putAction(Action action) {
        Timber.i(String.format("Saving Action %s now has status %d", action.shortDescription(),
                action.status()));
        return mActionsService.putAction(action.id(), action)
            .concatMap(new Function<Action, ObservableSource<? extends Action>>() {
                @Override
                public ObservableSource<? extends Action> apply(@NonNull Action action)
                        throws Exception {
                    return mDatabaseHelper.putAction(action);
                }
            });
    }

    public Observable<String> login(String email, String password) {
        Timber.i(String.format("Attempting login as %s", email));
        return mAccountService.login(LoginDetails.builder().setEmail(email).setPassword(password).build())
                .map((token) -> mPreferencesHelper.setToken(token));
    }
}
