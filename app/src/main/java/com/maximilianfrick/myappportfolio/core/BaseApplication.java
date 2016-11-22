package com.maximilianfrick.myappportfolio.core;

import android.app.Application;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;

public class BaseApplication extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
      Injector.init(this);
   }
}
