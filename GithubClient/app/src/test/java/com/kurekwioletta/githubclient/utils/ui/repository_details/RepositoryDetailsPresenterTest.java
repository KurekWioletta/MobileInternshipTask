package com.kurekwioletta.githubclient.utils.ui.repository_details;

import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsContract;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RepositoryDetailsPresenterTest {

    private RepositoryDetailsPresenter<RepositoryDetailsContract.View> mRepositoryDetailsPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RepositoryDetailsContract.View mMockedRepositoryDetailsView;

    @Mock
    private GithubApiManager mMockedGithubApiManager;

    @Mock
    private CompositeDisposable mMockedCompositeDisposable;

    @Before
    public void setUp() {
        mRepositoryDetailsPresenter = new RepositoryDetailsPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable);

        mRepositoryDetailsPresenter.onAttach(mMockedRepositoryDetailsView);
    }

    @Test
    public void when_viewWasInitialized_setUpRepositoryDetails() {

        // arrange
        Repository mockedRepository = mock(Repository.class);

        // act
        mRepositoryDetailsPresenter.onViewInitialized(mockedRepository);

        // assert
        verify(mMockedRepositoryDetailsView).setUpRepositoryDetails(mockedRepository);
    }

    @After
    public void tearDown() {
        mRepositoryDetailsPresenter.onDetach();
    }
}
