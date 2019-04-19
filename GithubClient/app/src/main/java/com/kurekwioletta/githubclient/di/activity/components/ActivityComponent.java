package com.kurekwioletta.githubclient.di.activity.components;

import android.app.Activity;

import dagger.MembersInjector;

public interface ActivityComponent<A extends Activity> extends MembersInjector<A> {

}