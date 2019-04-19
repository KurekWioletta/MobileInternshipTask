package com.kurekwioletta.githubclient.di.activity;

import com.kurekwioletta.githubclient.di.ActivityKey;
import com.kurekwioletta.githubclient.di.activity.components.MainActivityComponent;
import com.kurekwioletta.githubclient.di.activity.components.RepositoriesListActivityComponent;
import com.kurekwioletta.githubclient.di.activity.components.RepositoryDetailsActivityComponent;
import com.kurekwioletta.githubclient.ui.main.MainActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(
        subcomponents = {
                MainActivityComponent.class,
                RepositoriesListActivityComponent.class,
                RepositoryDetailsActivityComponent.class
        })
public abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    public abstract ActivityComponentBuilder mainActivityComponentBuilder(MainActivityComponent.Builder impl);

    @Binds
    @IntoMap
    @ActivityKey(RepositoriesListActivity.class)
    public abstract ActivityComponentBuilder repositoriesListActivityComponentBuilder(RepositoriesListActivityComponent.Builder impl);

    @Binds
    @IntoMap
    @ActivityKey(RepositoryDetailsActivity.class)
    public abstract ActivityComponentBuilder repositoryDetailsActivityComponentBuilder(RepositoryDetailsActivityComponent.Builder impl);
}