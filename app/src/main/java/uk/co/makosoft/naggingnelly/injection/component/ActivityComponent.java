package uk.co.makosoft.naggingnelly.injection.component;

import dagger.Subcomponent;
import uk.co.makosoft.naggingnelly.injection.PerActivity;
import uk.co.makosoft.naggingnelly.injection.module.ActivityModule;
import uk.co.makosoft.naggingnelly.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
