package com.kurekwioletta.githubclient.ui.main;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.ui.base.BasePresenter;
import com.kurekwioletta.githubclient.utils.AppConstants;
import com.kurekwioletta.githubclient.utils.Validator;
import com.kurekwioletta.githubclient.utils.rx.DisposableObserverWrapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

public class MainPresenter<V extends MainContract.View> extends BasePresenter<V> implements MainContract.Presenter<V>{

    @Inject
    public MainPresenter(GithubApiManager githubApiManager,  CompositeDisposable compositeDisposable) {
        super(githubApiManager, compositeDisposable);
    }

    @Override
    public void onLoadRepositoriesClick(final String username) {
        if (Validator.isUsernameValid(username)) {
            getMvpView().showLoading();

            // if user exists - open RepositoriesListActivity
            getCompositeDisposable().add(getGithubApiManager()
                .getUserResponse(username)
                .subscribeWith(new DisposableObserverWrapper<Response<Void>>() {
                    @Override
                    public void onNext(Response<Void> response) {
                        getMvpView().hideLoading();
                        switch (response.code()) {
                            case AppConstants.STATUS_CODE_SUCCESS:
                                getMvpView().openRepositoriesListActivity(username);
                                break;
                            case AppConstants.STATUS_CODE_NOT_FOUND:
                                getMvpView().showMessage(R.string.ERR_NOT_FOUND);
                                break;
                            default:
                                getMvpView().showMessage(R.string.ERR_UNKNOWN);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage(R.string.ERR_NETWORK);
                    }
                })
            );
        }
        else {
            getMvpView().showMessage(R.string.MSG_INVALID_USERNAME);
        }
    }
}
