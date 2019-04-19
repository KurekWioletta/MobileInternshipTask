package com.kurekwioletta.githubclient.di.app;

import com.kurekwioletta.githubclient.data.AppGithubApiManager;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.utils.rx.AppSchedulerProvider;
import com.kurekwioletta.githubclient.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class GithubApiModule {

    @Provides
    @Singleton
    GithubApiManager provideGithubApiManager(AppGithubApiManager appGithubApiManager) {
        return appGithubApiManager;
    }

    @Provides
    @Singleton
    AppGithubApiManager provideAppGithubApiManager(OkHttpClient okHttpClient, SchedulerProvider schedulerProvider) {
        return new AppGithubApiManager(okHttpClient, schedulerProvider);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
