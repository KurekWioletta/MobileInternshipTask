package com.kurekwioletta.githubclient.di;

import android.app.Activity;

import dagger.MapKey;

@MapKey
public @interface ActivityKey {
    Class<? extends Activity> value();
}