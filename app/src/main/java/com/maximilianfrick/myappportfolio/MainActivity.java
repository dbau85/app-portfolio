package com.maximilianfrick.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.maximilianfrick.myappportfolio.movies.PopularMoviesActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_build_it_bigger, R.id.btn_capstone,
            R.id.btn_go_ubiqutous, R.id.btn_make_your_app_material, R.id.btn_stock_hawk})
    public void onClickProject(Button button) {
        showToast(button.getText()
                .toString());
    }

    @OnClick(R.id.btn_popular_movies)
    public void onClickMovies() {
        startActivity(PopularMoviesActivity.newIntent(this));
    }

    private void showToast(String text) {
        Toast.makeText(this, getString(R.string.toast_launch_app, text), Toast.LENGTH_SHORT)
                .show();
    }
}
