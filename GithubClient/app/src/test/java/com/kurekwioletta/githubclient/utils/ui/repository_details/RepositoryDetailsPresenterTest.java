package com.kurekwioletta.githubclient.utils.ui.repository_details;

import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsContract;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RepositoryDetailsPresenterTest {

    private RepositoryDetailsPresenter<RepositoryDetailsContract.View> mRepositoryDetailsPresenter;

    @Mock
    RepositoryDetailsContract.View mMockedRepositoryDetailsView;

    @Mock
    CompositeDisposable mMockedCompositeDisposable;

    @Mock
    GithubApiManager mMockedGithubApiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mRepositoryDetailsPresenter = new RepositoryDetailsPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable);

        mRepositoryDetailsPresenter.onAttach(mMockedRepositoryDetailsView);
    }

    @Test
    public void when_viewInitialized_setUpRepositoryDetails() {
        Repository mockedRepository = mock(Repository.class);

        mRepositoryDetailsPresenter.onViewInitialized(mockedRepository);

        verify(mMockedRepositoryDetailsView).setUpRepositoryDetails(mockedRepository);
    }

    @After
    public void tearDown() {
        mRepositoryDetailsPresenter.onDetach();
    }
}
