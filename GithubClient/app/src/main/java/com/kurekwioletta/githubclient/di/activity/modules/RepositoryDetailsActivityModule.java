package com.kurekwioletta.githubclient.di.activity.modules;


import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsContract;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryDetailsActivityModule extends ActivityModule<RepositoryDetailsActivity> {
    public RepositoryDetailsActivityModule(RepositoryDetailsActivity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    RepositoryDetailsContract.Presenter<RepositoryDetailsContract.View> provideRepositoriesPresenter(
            RepositoryDetailsPresenter<RepositoryDetailsContract.View> presenter) {
        return presenter;
    }

}