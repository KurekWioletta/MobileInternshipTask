package com.kurekwioletta.githubclient.di.activity;

import com.kurekwioletta.githubclient.di.activity.components.ActivityComponent;
import com.kurekwioletta.githubclient.di.activity.modules.ActivityModule;

public interface ActivityComponentBuilder<M extends ActivityModule, C extends ActivityComponent> {

    ActivityComponentBuilder<M, C> activityModule(M activityModule);

    C build();

}