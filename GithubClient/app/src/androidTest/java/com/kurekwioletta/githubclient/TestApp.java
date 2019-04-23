package com.kurekwioletta.githubclient;

import android.app.Activity;

import com.kurekwioletta.githubclient.di.activity.ActivityComponentBuilder;

import java.util.HashMap;
import java.util.Map;

public class TestApp extends GithubClientApp {

    public void putActivityComponentBuilder(ActivityComponentBuilder builder, Class<? extends Activity> cls) {
        Map<Class<? extends Activity>, ActivityComponentBuilder> activityComponentBuilders = new HashMap<>(mActivityComponentBuilders);
        activityComponentBuilders.put(cls, builder);
        mActivityComponentBuilders = activityComponentBuilders;
    }

}
