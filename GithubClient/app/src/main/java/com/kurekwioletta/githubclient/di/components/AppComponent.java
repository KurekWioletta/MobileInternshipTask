package com.kurekwioletta.githubclient.di.components;

import android.content.Context;

import com.kurekwioletta.githubclient.GithubClientApp;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.di.ApplicationContext;
import com.kurekwioletta.githubclient.di.modules.AppModule;
import com.kurekwioletta.githubclient.di.modules.GithubApiModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {
                AppModule.class,
                GithubApiModule.class
        })
public interface AppComponent extends AndroidInjector<GithubClientApp> {

    void inject(GithubClientApp githubClientApp);

    @ApplicationContext
    Context context();

    GithubApiManager getGithubApiManager();

}