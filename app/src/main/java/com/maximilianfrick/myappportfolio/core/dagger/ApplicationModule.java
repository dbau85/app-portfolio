package com.maximilianfrick.myappportfolio.core.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.maximilianfrick.myappportfolio.core.BaseApplication;
import com.maximilianfrick.myappportfolio.movies.detail.MovieFavoritesController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private static final String MY_SHARED_PREFS = "my_shared_prefs";
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
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    MovieFavoritesController provideMovieFavoritesController(SharedPreferences sharedPreferences) {
        return new MovieFavoritesController(sharedPreferences);
    }
}
