package com.kurekwioletta.githubclient.utils.ui.repositories_list;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListContract;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListPresenter;
import com.kurekwioletta.githubclient.utils.utils.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoriesListPresenterTest {

    private RepositoriesListPresenter<RepositoriesListContract.View> mRepositoriesListPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RepositoriesListContract.View mMockedRepositoriesListView;

    @Mock
    private GithubApiManager mMockedGithubApiManager;

    @Mock
    private CompositeDisposable mMockedCompositeDisposable;

    @Before
    public void setUp() {
        mRepositoriesListPresenter = new RepositoriesListPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable);

        mRepositoriesListPresenter.onAttach(mMockedRepositoriesListView);
    }

    @Test
    public void when_repositoriesListWasLoadedSuccessfully_updateRepositoriesList() {

        // arrange - successfully loaded repositories
        List<Repository> repositoriesList = new ArrayList<>();

        when(mMockedGithubApiManager.getUsersRepositoriesList(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(repositoriesList));

        // act
        mRepositoriesListPresenter.onViewInitialized(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedRepositoriesListView).showLoading();
        verify(mMockedRepositoriesListView).hideLoading();
        verify(mMockedRepositoriesListView).updateRepositoriesListRecyclerView(repositoriesList);
    }

    @Test
    public void when_networkErrorHasOccurred_showNetworkErrorMessage() {

        // arrange - network error
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
