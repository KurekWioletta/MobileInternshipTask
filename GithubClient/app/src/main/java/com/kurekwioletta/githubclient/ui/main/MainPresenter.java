package com.kurekwioletta.githubclient.ui.main;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.di.ActivityScope;
import com.kurekwioletta.githubclient.ui.base.BasePresenter;
import com.kurekwioletta.githubclient.utils.Validator;
import com.kurekwioletta.githubclient.utils.rx.DisposableObserverWrapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

@ActivityScope
public class MainPresenter<V extends MainContract.View> extends BasePresenter<V> implements MainContract.Presenter<V> {

    private static final int RESPONSE_STATUS_CODE_SUCCESS = 200;
    private static final int RESPONSE_STATUS_CODE_NOT_FOUND = 404;

    private Validator mValidator;

    @Inject
    public MainPresenter(GithubApiManager githubApiManager, CompositeDisposable compositeDisposable, Validator validator) {
        super(githubApiManager, compositeDisposable);
        mValidator = validator;
    }

    @Override
    public void onLoadRepositoriesClick(final String username) {
        if (mValidator.isUsernameValid(username)) {
            getMvpView().showLoading();

            // if user exists - open RepositoriesListActivity
            getCompositeDisposable().add(
                    getGithubApiManager().getUserResponse(username)
                            .subscribeWith(new DisposableObserverWrapper<Response<Void>>() {
                                @Override
                                public void onNext(Response<Void> response) {
                                    getMvpView().hideLoading();
                                    switch (response.code()) {
                                        case RESPONSE_STATUS_CODE_SUCCESS:
                                            getMvpView().openRepositoriesListActivity(username);
                                            break;
                                        case RESPONSE_STATUS_CODE_NOT_FOUND:
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
        } else {
            getMvpView().showMessage(R.string.MSG_INVALID_USERNAME);
        }
    }
}
