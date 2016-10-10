package com.maximilianfrick.myappportfolio.core;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {

    BaseApplication application();

    void inject(BaseActivity baseActivity);
}
