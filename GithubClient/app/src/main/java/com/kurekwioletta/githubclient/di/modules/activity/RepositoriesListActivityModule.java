package com.kurekwioletta.githubclient.di.modules.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.kurekwioletta.githubclient.di.PerActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListContract;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListPresenter;
import com.kurekwioletta.githubclient.ui.repositories_list.adapter.RepositoriesListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesListActivityModule {

    @Provides
    @PerActivity
    RepositoriesListContract.Presenter<RepositoriesListContract.View> provideRepositoriesPresenter(
            RepositoriesListPresenter<RepositoriesListContract.View> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RepositoriesListAdapter provideRepositoriesListAdapter(AppCompatActivity activity) {
        return new RepositoriesListAdapter(activity);
    }

    @Provides
    @PerActivity
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
