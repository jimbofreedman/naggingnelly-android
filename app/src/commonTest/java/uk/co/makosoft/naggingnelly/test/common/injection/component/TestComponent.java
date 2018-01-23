package uk.co.makosoft.naggingnelly.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.makosoft.naggingnelly.injection.component.ApplicationComponent;
import uk.co.makosoft.naggingnelly.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
