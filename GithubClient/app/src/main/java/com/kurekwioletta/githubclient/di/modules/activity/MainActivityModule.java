package com.kurekwioletta.githubclient.di.modules.activity;

import com.kurekwioletta.githubclient.di.PerActivity;
import com.kurekwioletta.githubclient.ui.main.MainContract;
import com.kurekwioletta.githubclient.ui.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @PerActivity
    MainContract.Presenter<MainContract.View> provideMainPresenter(
            MainPresenter<MainContract.View> presenter) {
        return presenter;
    }

}
