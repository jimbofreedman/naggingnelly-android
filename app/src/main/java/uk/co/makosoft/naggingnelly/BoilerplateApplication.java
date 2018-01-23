package uk.co.makosoft.naggingnelly;

import android.app.Application;
import android.content.Context;

//import com.crashlytics.android.Crashlytics;

//import com.crashlytics.android.ndk.CrashlyticsNdk;
//import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import uk.co.makosoft.naggingnelly.injection.component.ApplicationComponent;
import uk.co.makosoft.naggingnelly.injection.component.DaggerApplicationComponent;
import uk.co.makosoft.naggingnelly.injection.module.ApplicationModule;

public class BoilerplateApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        }
    }

    public static BoilerplateApplication get(Context context) {
        return (BoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
