package com.kurekwioletta.githubclient;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.kurekwioletta.githubclient.di.activity.ActivityComponentBuilder;
import com.kurekwioletta.githubclient.di.activity.HasActivitySubcomponentBuilders;
import com.kurekwioletta.githubclient.di.app.AppComponent;
import com.kurekwioletta.githubclient.di.app.DaggerAppComponent;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

public class GithubClientApp extends Application implements HasActivitySubcomponentBuilders {

    @Inject
    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> mActivityComponentBuilders;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.create();

        appComponent.inject(this);
    }

    public static HasActivitySubcomponentBuilders get(Context context) {
        return ((HasActivitySubcomponentBuilders) context.getApplicationContext());
    }

    @Override
    public ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass) {
        return Objects.requireNonNull(mActivityComponentBuilders.get(activityClass)).get();
    }
}
