package com.kurekwioletta.githubclient.data;

import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Response;

@Singleton
public class AppGithubApiManager implements GithubApiManager {

    private final GithubApiManager mGithubApiManager;
    private final SchedulerProvider mSchedulerProvider;

    public AppGithubApiManager(GithubApiManager githubApiManager, SchedulerProvider schedulerProvider) {
        mGithubApiManager = githubApiManager;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<Response<Void>> getUserResponse(String username) {
        return mGithubApiManager.getUserResponse(username)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }

    @Override
    public Observable<List<Repository>> getUsersRepositoriesList(String username) {
        return mGithubApiManager.getUsersRepositoriesList(username)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }
}
