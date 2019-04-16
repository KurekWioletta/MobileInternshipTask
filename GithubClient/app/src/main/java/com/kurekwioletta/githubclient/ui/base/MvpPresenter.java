package com.kurekwioletta.githubclient.ui.base;

public interface MvpPresenter<V> {

    void onAttach(V mvpView);

    void onDetach();

}