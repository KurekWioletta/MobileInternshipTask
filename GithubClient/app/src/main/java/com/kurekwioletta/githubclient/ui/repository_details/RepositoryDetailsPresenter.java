package com.kurekwioletta.githubclient.ui.repository_details;

import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RepositoryDetailsPresenter<V extends RepositoryDetailsContract.View> extends BasePresenter<V> implements RepositoryDetailsContract.Presenter<V> {

    @Inject
    public RepositoryDetailsPresenter(GithubApiManager githubApiManager, CompositeDisposable compositeDisposable) {
        super(githubApiManager, compositeDisposable);
    }

    @Override
    public void onViewInitialized(Repository repository) {
        getMvpView().setUpRepositoryDetails(repository);
    }
}
