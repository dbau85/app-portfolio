package com.maximilianfrick.myappportfolio.core;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    BaseApplication provideBaseApplication() {
        return application;
    }

    @Provides
    @Singleton
    AppFlowController provideAppFlowController() {
        return new AppFlowController();
    }
}
