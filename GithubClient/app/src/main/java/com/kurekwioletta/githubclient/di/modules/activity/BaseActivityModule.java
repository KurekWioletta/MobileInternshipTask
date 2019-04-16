package com.kurekwioletta.githubclient.di.modules.activity;

import android.support.v7.app.AppCompatActivity;

import com.kurekwioletta.githubclient.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class BaseActivityModule {

    private AppCompatActivity mActivity;

    public BaseActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
