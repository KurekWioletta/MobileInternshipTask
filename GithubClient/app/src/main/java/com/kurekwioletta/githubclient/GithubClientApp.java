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

public class GithubClientApp extends Application implements HasActivitySubcomponentBuilders {

    @Inject
    protected Map<Class<? extends Activity>, ActivityComponentBuilder> mActivityComponentBuilders;

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
    }

    @Override
    public ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass) {
        return Objects.requireNonNull(mActivityComponentBuilders.get(activityClass));
    }

    public static HasActivitySubcomponentBuilders get(Context context) {
        return ((HasActivitySubcomponentBuilders) context.getApplicationContext());
    }

}
