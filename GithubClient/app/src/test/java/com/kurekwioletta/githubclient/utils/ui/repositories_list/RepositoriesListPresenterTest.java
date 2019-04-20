package com.kurekwioletta.githubclient.utils.ui.repositories_list;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListContract;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListPresenter;
import com.kurekwioletta.githubclient.utils.utils.TestConstants;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoriesListPresenterTest {

    private RepositoriesListPresenter<RepositoriesListContract.View> mRepositoriesListPresenter;

    @Mock
    RepositoriesListContract.View mMockedRepositoriesListView;

    @Mock
    CompositeDisposable mMockedCompositeDisposable;

    @Mock
    GithubApiManager mMockedGithubApiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mRepositoriesListPresenter = new RepositoriesListPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable);

        mRepositoriesListPresenter.onAttach(mMockedRepositoriesListView);
    }

    @Test
    public void when_repositoriesListWasLoadedSuccessfully_updateRepositoriesList() {

        // arrange
        List<Repository> repositoryList = new ArrayList<>();

        doReturn(Observable.just(repositoryList))
                .when(mMockedGithubApiManager)
                .getUsersRepositoriesList(TestConstants.VALID_USERNAME);

        // act
        mRepositoriesListPresenter.onViewInitialized(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedRepositoriesListView).showLoading();
        verify(mMockedRepositoriesListView).hideLoading();
        verify(mMockedRepositoriesListView).updateRepositoriesListRecyclerView(repositoryList);
    }

    @Test
    public void when_networkErrorHasOccurred_showNetworkErrorMessage() {

        // arrange
        when(mMockedGithubApiManager.getUsersRepositoriesList(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        // act
        mRepositoriesListPresenter.onViewInitialized(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedRepositoriesListView).showLoading();
        verify(mMockedRepositoriesListView).hideLoading();
        verify(mMockedRepositoriesListView).showMessage(R.string.ERR_NETWORK);
    }

    @After
    public void tearDown() {
        mRepositoriesListPresenter.onDetach();
    }
}
