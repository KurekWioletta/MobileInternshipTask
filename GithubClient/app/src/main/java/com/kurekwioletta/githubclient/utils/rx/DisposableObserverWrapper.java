package com.kurekwioletta.githubclient.utils.rx;

import io.reactivex.observers.DisposableObserver;

public abstract class DisposableObserverWrapper<T> extends DisposableObserver<T> {

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
    }

}