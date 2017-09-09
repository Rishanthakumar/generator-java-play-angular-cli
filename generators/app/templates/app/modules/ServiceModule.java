package modules;

import com.google.inject.AbstractModule;
import services.SampleService;
import services.SampleServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SampleService.class).to(SampleServiceImpl.class);
    }
}
