package com.kurekwioletta.githubclient.di.components;

import com.kurekwioletta.githubclient.di.PerActivity;
import com.kurekwioletta.githubclient.di.modules.activity.BaseActivityModule;
import com.kurekwioletta.githubclient.di.modules.activity.MainActivityModule;
import com.kurekwioletta.githubclient.di.modules.activity.RepositoriesListActivityModule;
import com.kurekwioletta.githubclient.di.modules.activity.RepositoryDetailsActivityModule;
import com.kurekwioletta.githubclient.ui.main.MainActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {
                BaseActivityModule.class,
                MainActivityModule.class,
                RepositoriesListActivityModule.class,
                RepositoryDetailsActivityModule.class
        })
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(RepositoryDetailsActivity activity);

    void inject(RepositoriesListActivity activity);

}