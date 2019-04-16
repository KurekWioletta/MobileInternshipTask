package com.kurekwioletta.githubclient.di.modules.activity;

import com.kurekwioletta.githubclient.di.PerActivity;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsContract;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryDetailsActivityModule {

    @Provides
    @PerActivity
    RepositoryDetailsContract.Presenter<RepositoryDetailsContract.View> provideRepositoriesPresenter(
            RepositoryDetailsPresenter<RepositoryDetailsContract.View> presenter) {
        return presenter;
    }

}
