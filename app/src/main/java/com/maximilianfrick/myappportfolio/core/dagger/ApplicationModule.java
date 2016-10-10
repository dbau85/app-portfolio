package com.maximilianfrick.myappportfolio.core.dagger;

import android.content.Context;

import com.maximilianfrick.myappportfolio.core.AppFlowController;
import com.maximilianfrick.myappportfolio.core.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private BaseApplication application;
    private Context context;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
        this.context = application.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
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
