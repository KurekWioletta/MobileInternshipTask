package com.kurekwioletta.githubclient.ui.repositories_list;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.base.BasePresenter;
import com.kurekwioletta.githubclient.utils.rx.DisposableObserverWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RepositoriesListPresenter<V extends RepositoriesListContract.View> extends BasePresenter<V> implements RepositoriesListContract.Presenter<V> {

    @Inject
    public RepositoriesListPresenter(GithubApiManager githubApiManager, CompositeDisposable compositeDisposable) {
        super(githubApiManager, compositeDisposable);
    }

    @Override
    public void onViewInitialized(String username) {
        getMvpView().showLoading();

        // download users repositories list
        getCompositeDisposable().add(getGithubApiManager()
                .getUsersRepositoriesList(username)
                .subscribeWith(new DisposableObserverWrapper<List<Repository>>() {
                    @Override
                    public void onNext(List<Repository> repositoriesList) {
                        getMvpView().hideLoading();
                        getMvpView().updateRepositoriesListRecyclerView(repositoriesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage(R.string.ERR_NETWORK);
                    }
                })
        );
    }

}
