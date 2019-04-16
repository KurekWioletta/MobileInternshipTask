package com.kurekwioletta.githubclient.data;

import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.utils.AppConstants;
import com.kurekwioletta.githubclient.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppGithubApiManager implements GithubApiManager {

    private final GithubApiManager mGithubApiHelper;
    private final SchedulerProvider mSchedulerProvider;

    @Inject
    public AppGithubApiManager(OkHttpClient okHttpClient, SchedulerProvider schedulerProvider) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppConstants.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mGithubApiHelper = retrofit.create(GithubApiManager.class);
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<Response<Void>> getUserResponse(String username) {
        return mGithubApiHelper.getUserResponse(username)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }

    @Override
    public Observable<List<Repository>> getUsersRepositoriesList(String username) {
        return mGithubApiHelper.getUsersRepositoriesList(username)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }
}
