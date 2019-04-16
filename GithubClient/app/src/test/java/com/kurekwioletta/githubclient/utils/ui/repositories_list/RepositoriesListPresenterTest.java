package com.kurekwioletta.githubclient.utils.ui.repositories_list;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListContract;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListPresenter;
import com.kurekwioletta.githubclient.utils.utils.TestConstans;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoriesListPresenterTest {

    private RepositoriesListPresenter<RepositoriesListContract.View> mRepositoriesListPresenter;

    @Mock
    RepositoriesListContract.View mMockRepositoryListView;

    @Mock
    GithubApiManager mMockGithubApiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mRepositoriesListPresenter = new RepositoriesListPresenter<>(
                mMockGithubApiManager,
                compositeDisposable);

        mRepositoriesListPresenter.onAttach(mMockRepositoryListView);
    }

    @Test
    public void when_repositoriesLoadedSuccessfully_updateRepositoriesList() {
        List<Repository> repositoryList = new ArrayList<>();

        doReturn(Observable.just(repositoryList))
                .when(mMockGithubApiManager)
                .getUsersRepositoriesList(TestConstans.VALID_USERNAME);

        mRepositoriesListPresenter.onViewInitialized(TestConstans.VALID_USERNAME);

        verify(mMockRepositoryListView).showLoading();
        verify(mMockRepositoryListView).hideLoading();
        verify(mMockRepositoryListView).updateRepositoriesListRecyclerView(repositoryList);
    }

    /**
     * Show message when api request code is other than 200
     * or there was an internet connection error.
     */
    @Test
    public void when_failedToLoadRepositories_showMessage() {
        when(mMockGithubApiManager.getUsersRepositoriesList(TestConstans.VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        mRepositoriesListPresenter.onViewInitialized(TestConstans.VALID_USERNAME);

        verify(mMockRepositoryListView).showLoading();
        verify(mMockRepositoryListView).hideLoading();
        verify(mMockRepositoryListView).showMessage(R.string.ERR_NETWORK);
    }

    @After
    public void close() {
        mRepositoriesListPresenter.onDetach();
    }
}
