package com.kurekwioletta.githubclient.di.activity.modules;

import com.kurekwioletta.githubclient.ui.main.MainActivity;

import dagger.Module;

@Module
public class MainActivityModule extends ActivityModule<MainActivity> {

    public MainActivityModule(MainActivity activity) {
        super(activity);
    }

}