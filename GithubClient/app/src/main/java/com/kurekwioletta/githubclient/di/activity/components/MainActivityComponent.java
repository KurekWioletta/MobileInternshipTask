package com.kurekwioletta.githubclient.di.activity.components;

import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.di.activity.ActivityComponentBuilder;
import com.kurekwioletta.githubclient.di.activity.modules.MainActivityModule;
import com.kurekwioletta.githubclient.ui.main.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = MainActivityModule.class
)
public interface MainActivityComponent extends ActivityComponent<MainActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<MainActivityModule, MainActivityComponent> {
    }

}