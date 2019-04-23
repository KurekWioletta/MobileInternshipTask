package com.kurekwioletta.githubclient.di.app;

import com.kurekwioletta.githubclient.BuildConfig;
import com.kurekwioletta.githubclient.data.AppGithubApiManager;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.utils.rx.AppSchedulerProvider;
import com.kurekwioletta.githubclient.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GithubApiModule {

    @Provides
    @Singleton
    GithubApiManager provideGithubApiManager(AppGithubApiManager appGithubApiManager) {
        return appGithubApiManager;
    }

    @Provides
    @Singleton
    AppGithubApiManager provideAppGithubApiManager(Retrofit retrofit, SchedulerProvider schedulerProvider) {
        GithubApiManager githubApiManager = retrofit.create(GithubApiManager.class);
        return new AppGithubApiManager(githubApiManager, schedulerProvider);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
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