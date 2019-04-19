package com.kurekwioletta.githubclient.di.activity;

import android.app.Activity;

public interface HasActivitySubcomponentBuilders {

    ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass);

}
