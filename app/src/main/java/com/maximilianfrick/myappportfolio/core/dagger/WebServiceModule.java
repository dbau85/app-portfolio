package com.maximilianfrick.myappportfolio.core.dagger;

import android.content.Context;

import com.maximilianfrick.myappportfolio.BuildConfig;
import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.MoviesService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class WebServiceModule {

    private static final long CACHE_SIZE = 25 * 1024 * 1024;

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        final File cacheDirectory = new File(context.getCacheDir()
                .getAbsolutePath(), "HttpCache");
        final Cache cache = new Cache(cacheDirectory, CACHE_SIZE);
        builder.cache(cache);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context, OkHttpClient httpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(context.getString(R.string.base_url_themoviedb))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient);
        return builder.build();
    }

    @Provides
    @Singleton
    MoviesService provideMoviesService(Retrofit retrofit) {
        return retrofit.create(MoviesService.class);
    }

}
