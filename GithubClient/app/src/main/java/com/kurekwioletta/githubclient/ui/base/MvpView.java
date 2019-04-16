package com.kurekwioletta.githubclient.ui.base;

import android.support.annotation.StringRes;

public interface MvpView<T>  {

    void showMessage(@StringRes int resId);

}
