package com.kurekwioletta.githubclient.ui.main;

import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.base.MvpPresenter;
import com.kurekwioletta.githubclient.ui.base.MvpView;

public interface MainContract {
    interface View extends MvpView {
        void openRepositoriesListActivity(String username);

        void showLoading();

        void hideLoading();
    }

    @ActivityScope
    interface Presenter<V extends View> extends MvpPresenter<V> {
        void onLoadRepositoriesClick(String username);
    }
}
