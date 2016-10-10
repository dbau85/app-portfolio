package com.maximilianfrick.myappportfolio.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;

import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected AppFlowController appFlowController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.getAppComponent().inject(this);
    }

    protected AppFlowController getAppFlowController() {
        return appFlowController;
    }
}
