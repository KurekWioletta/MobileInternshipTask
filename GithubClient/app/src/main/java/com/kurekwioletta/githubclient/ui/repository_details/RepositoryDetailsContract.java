package com.kurekwioletta.githubclient.ui.repository_details;

import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.base.MvpPresenter;
import com.kurekwioletta.githubclient.ui.base.MvpView;

public interface RepositoryDetailsContract {
    interface View extends MvpView {
        void setUpRepositoryDetails(Repository repository);
    }

    @ActivityScope
    interface Presenter<V extends RepositoryDetailsContract.View> extends MvpPresenter<V> {
        void onViewInitialized(Repository repository);
    }
}