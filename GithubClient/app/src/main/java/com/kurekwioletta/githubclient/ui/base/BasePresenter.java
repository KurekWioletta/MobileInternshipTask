package com.kurekwioletta.githubclient.ui.base;

import com.kurekwioletta.githubclient.data.GithubApiManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final GithubApiManager mGithubApiManager;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(GithubApiManager githubApiManager, CompositeDisposable compositeDisposable) {
        mGithubApiManager = githubApiManager;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    protected GithubApiManager getGithubApiManager() {
        return mGithubApiManager;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}