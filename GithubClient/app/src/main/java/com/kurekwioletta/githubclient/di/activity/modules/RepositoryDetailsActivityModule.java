package com.kurekwioletta.githubclient.di.activity.modules;

import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;

import dagger.Module;

@Module
public class RepositoryDetailsActivityModule extends ActivityModule<RepositoryDetailsActivity> {

    public RepositoryDetailsActivityModule(RepositoryDetailsActivity activity) {
        super(activity);
    }

}