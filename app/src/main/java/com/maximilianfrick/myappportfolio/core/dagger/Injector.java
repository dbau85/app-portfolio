package com.maximilianfrick.myappportfolio.core.dagger;

import com.maximilianfrick.myappportfolio.core.BaseApplication;

public final class Injector {
   private static AppComponent appComponent;

   public static AppComponent getAppComponent() {
      return appComponent;
   }

   public static void init(BaseApplication application) {
      appComponent = DaggerAppComponent.builder()
            .applicationModule(new ApplicationModule(application))
            .build();
   }
}
