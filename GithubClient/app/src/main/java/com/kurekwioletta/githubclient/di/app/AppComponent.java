package com.kurekwioletta.githubclient.di.app;

import com.kurekwioletta.githubclient.GithubClientApp;
import com.kurekwioletta.githubclient.di.activity.ActivityBindingModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                GithubApiModule.class,
                ActivityBindingModule.class
        })
public interface AppComponent {

    void inject(GithubClientApp githubClientApp);

}