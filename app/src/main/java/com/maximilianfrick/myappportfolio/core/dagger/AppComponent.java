package com.maximilianfrick.myappportfolio.core.dagger;

import com.maximilianfrick.myappportfolio.core.BaseApplication;
import com.maximilianfrick.myappportfolio.movies.MoviesPresenter;
import com.maximilianfrick.myappportfolio.movies.PopularMoviesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, WebServiceModule.class})
public interface AppComponent {

    BaseApplication application();

    void inject(PopularMoviesActivity popularMoviesActivity);

    void inject(MoviesPresenter moviesPresenter);

}
