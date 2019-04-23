package com.kurekwioletta.githubclient.di.activity.modules;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesListActivityModule extends ActivityModule<RepositoriesListActivity> {

    public RepositoriesListActivityModule(RepositoriesListActivity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    LinearLayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}