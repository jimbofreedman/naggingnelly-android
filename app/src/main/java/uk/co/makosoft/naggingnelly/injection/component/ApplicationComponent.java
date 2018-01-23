package uk.co.makosoft.naggingnelly.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.makosoft.naggingnelly.data.DataManager;
import uk.co.makosoft.naggingnelly.data.SyncService;
import uk.co.makosoft.naggingnelly.data.local.DatabaseHelper;
import uk.co.makosoft.naggingnelly.data.local.PreferencesHelper;
import uk.co.makosoft.naggingnelly.data.remote.FoldersService;
import uk.co.makosoft.naggingnelly.data.remote.RibotsService;
import uk.co.makosoft.naggingnelly.injection.ApplicationContext;
import uk.co.makosoft.naggingnelly.injection.module.ApplicationModule;
import uk.co.makosoft.naggingnelly.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    FoldersService foldersService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
