package com.kurekwioletta.githubclient.utils.ui.repository_details;

import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsContract;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsPresenter;

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
    RepositoryDetailsContract.View mMockRepositoryDetailsView;

    @Mock
    GithubApiManager mMockGithubApiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mRepositoryDetailsPresenter = new RepositoryDetailsPresenter<>(
                mMockGithubApiManager,
                compositeDisposable);

        mRepositoryDetailsPresenter.onAttach(mMockRepositoryDetailsView);
    }

    @Test
    public void when_viewInitialized_setUpRepositoryDetails() {
        Repository mockRepository = mock(Repository.class);

        mRepositoryDetailsPresenter.onViewInitialized(mockRepository);

        verify(mMockRepositoryDetailsView).setUpRepositoryDetails(mockRepository);
    }
}
