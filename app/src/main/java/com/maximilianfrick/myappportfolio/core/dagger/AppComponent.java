package com.maximilianfrick.myappportfolio.core.dagger;

import com.maximilianfrick.myappportfolio.core.BaseApplication;
import com.maximilianfrick.myappportfolio.movies.detail.MoviesDetailActivity;
import com.maximilianfrick.myappportfolio.movies.detail.MoviesDetailPresenter;
import com.maximilianfrick.myappportfolio.movies.list.MoviesActivity;
import com.maximilianfrick.myappportfolio.movies.list.MoviesPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { ApplicationModule.class, WebServiceModule.class })
public interface AppComponent {

   BaseApplication application();

   void inject(MoviesActivity moviesActivity);

   void inject(MoviesPresenter moviesPresenter);

   void inject(MoviesDetailPresenter moviesDetailPresenter);

   void inject(MoviesDetailActivity moviesDetailActivity);
}
