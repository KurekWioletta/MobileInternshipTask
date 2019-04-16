package com.kurekwioletta.githubclient.data;

import com.kurekwioletta.githubclient.data.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiManager {
    @GET("users/{username}")
    Observable<Response<Void>> getUserResponse(@Path("username") String username);

    @GET("users/{username}/repos")
    Observable<List<Repository>> getUsersRepositoriesList(@Path("username") String username);
}