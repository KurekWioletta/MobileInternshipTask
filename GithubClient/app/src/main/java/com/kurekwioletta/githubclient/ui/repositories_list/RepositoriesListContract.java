package com.kurekwioletta.githubclient.ui.repositories_list;

import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.di.PerActivity;
import com.kurekwioletta.githubclient.ui.base.MvpPresenter;
import com.kurekwioletta.githubclient.ui.base.MvpView;

import java.util.List;

public interface RepositoriesListContract {
    interface View extends MvpView {
        void updateRepositoriesListRecyclerView(List<Repository> repositoriesList);

        void showLoading();

        void hideLoading();
    }

    @PerActivity
    interface Presenter<V extends RepositoriesListContract.View> extends MvpPresenter<V> {
        void onViewInitialized(String username);
    }
}
