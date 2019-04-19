package com.kurekwioletta.githubclient.di.activity.modules;


import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.main.MainActivity;
import com.kurekwioletta.githubclient.ui.main.MainContract;
import com.kurekwioletta.githubclient.ui.main.MainPresenter;
import com.kurekwioletta.githubclient.utils.Validator;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule extends ActivityModule<MainActivity> {
    public MainActivityModule(MainActivity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    MainContract.Presenter<MainContract.View> provideMainPresenter(
            MainPresenter<MainContract.View> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    Validator provideValidator() {
        return new Validator();
    }
}