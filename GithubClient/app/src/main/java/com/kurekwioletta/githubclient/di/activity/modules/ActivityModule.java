package com.kurekwioletta.githubclient.di.activity.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.kurekwioletta.githubclient.di.ActivityScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class ActivityModule<A extends AppCompatActivity> {

    private final A mActivity;

    ActivityModule(A activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    A provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
