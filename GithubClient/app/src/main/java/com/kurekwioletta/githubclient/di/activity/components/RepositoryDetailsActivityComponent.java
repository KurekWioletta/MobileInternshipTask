package com.kurekwioletta.githubclient.di.activity.components;

import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.di.activity.ActivityComponentBuilder;
import com.kurekwioletta.githubclient.di.activity.modules.RepositoryDetailsActivityModule;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = RepositoryDetailsActivityModule.class
)
public interface RepositoryDetailsActivityComponent extends ActivityComponent<RepositoryDetailsActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<RepositoryDetailsActivityModule, RepositoryDetailsActivityComponent> {
    }
}