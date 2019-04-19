package com.kurekwioletta.githubclient.di.activity.components;

import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.di.activity.ActivityComponentBuilder;
import com.kurekwioletta.githubclient.di.activity.modules.RepositoriesListActivityModule;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = RepositoriesListActivityModule.class
)
public interface RepositoriesListActivityComponent extends ActivityComponent<RepositoriesListActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<RepositoriesListActivityModule, RepositoriesListActivityComponent> {
    }
}