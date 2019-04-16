package com.kurekwioletta.githubclient;

import android.app.Application;

import com.kurekwioletta.githubclient.di.components.AppComponent;
import com.kurekwioletta.githubclient.di.components.DaggerAppComponent;
import com.kurekwioletta.githubclient.di.modules.AppModule;

public class GithubClientApp extends Application {
    protected AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();

        mAppComponent.inject(this);
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

}
