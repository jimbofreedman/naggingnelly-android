package uk.co.makosoft.naggingnelly.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.makosoft.naggingnelly.data.remote.AccountService;
import uk.co.makosoft.naggingnelly.data.remote.ActionsService;
import uk.co.makosoft.naggingnelly.data.remote.FoldersService;
import uk.co.makosoft.naggingnelly.data.remote.RibotsService;
import uk.co.makosoft.naggingnelly.injection.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    AccountService provideAccountService() {
        return AccountService.Creator.newAccountService();
    }

    @Provides
    @Singleton
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }

    @Provides
    @Singleton
    FoldersService provideFoldersService() { return FoldersService.Creator.newFoldersService(); }

    @Provides
    @Singleton
    ActionsService provideActionsService() { return ActionsService.Creator.newActionsService(); }
}
