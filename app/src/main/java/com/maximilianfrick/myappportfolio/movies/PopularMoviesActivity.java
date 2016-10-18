package com.maximilianfrick.myappportfolio.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.BaseActivity;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PopularMoviesActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @Inject
    MoviesService moviesService;
    private ArrayList<String> images;
    private MovieListAdapter adapter;

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, PopularMoviesActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        Injector.getAppComponent().inject(this);
        images = new ArrayList<>();
        Observable<MoviesData> observable = moviesService.getPopularMovies();
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MoviesData>() {
            @Override
            public void call(MoviesData moviesData) {
                for (Movie movie : moviesData.getMovies()) {
                    images.add(PopularMoviesActivity.this.getString(R.string.base_url_images) + movie.getPosterPath());
                }
                adapter.setItems(images);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });


        //TODO: get Data and replace mock here
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieListAdapter(this, images);
        recyclerView.setAdapter(adapter);

        // Build GoogleApiClient with AppInvite API for receiving deep links
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(AppInvite.API)
                .build();

        // Check if this app was launched from a deep link. Setting autoLaunchDeepLink to true
        // would automatically launch the deep link if one is found.
        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    // Extract deep link from Intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);

                                    // Handle the deep link. For example, open the linked
                                    // content, or apply promotional credit to the user's
                                    // account.

                                    // ...
                                } else {
                                    Log.d("blubb", "getInvitation: no deep link found.");
                                }
                            }
                        });

    }
}
