package com.kurekwioletta.githubclient.utils.ui.main;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.ui.main.MainContract;
import com.kurekwioletta.githubclient.ui.main.MainPresenter;
import com.kurekwioletta.githubclient.utils.AppConstants;
import com.kurekwioletta.githubclient.utils.ui.main.response.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private static final String VALID_USERNAME = "KurekWioletta";
    private static final String INVALID_USERNAME = "/a";
    private static final int STATUS_CODE_UNKNOWN = 520;

    private MainPresenter<MainContract.View> mMainPresenter;

    @Mock
    MainContract.View mMockedMainView;

    @Mock
    CompositeDisposable mMockedCompositeDisposable;

    @Mock
    GithubApiManager mMockedGithubApiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable);

        mMainPresenter.onAttach(mMockedMainView);
    }

    @Test
    public void when_userExist_showRepositoriesList() {
        Response<User> response = Response.success(
                AppConstants.STATUS_CODE_SUCCESS,
                new User());

        doReturn(Observable.just(response))
                .when(mMockedGithubApiManager)
                .getUserResponse(VALID_USERNAME);

        mMainPresenter.onLoadRepositoriesClick(VALID_USERNAME);

        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).openRepositoriesListActivity(VALID_USERNAME);
    }

    @Test
    public void when_userNotFound_showMessage() {
        Response<User> response = Response.error(
                AppConstants.STATUS_CODE_NOT_FOUND,
                ResponseBody.create(
                        MediaType.parse("application/json"),""
                ));

        doReturn(Observable.just(response))
                .when(mMockedGithubApiManager)
                .getUserResponse(VALID_USERNAME);

        mMainPresenter.onLoadRepositoriesClick(VALID_USERNAME);

        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_NOT_FOUND);
    }

    @Test
    public void when_unknownError_showMessage() {
        Response<User> response = Response.error(
                STATUS_CODE_UNKNOWN,
                ResponseBody.create(
                        MediaType.parse("application/json"),""
                ));

        doReturn(Observable.just(response))
                .when(mMockedGithubApiManager)
                .getUserResponse(VALID_USERNAME);

        mMainPresenter.onLoadRepositoriesClick(VALID_USERNAME);

        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_UNKNOWN);
    }

    @Test
    public void when_networkError_showMessage() {
        when(mMockedGithubApiManager.getUserResponse(VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        mMainPresenter.onLoadRepositoriesClick(VALID_USERNAME);

        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_NETWORK);
    }

    @Test
    public void when_usernameIsInvalid_showMessage() {
        mMainPresenter.onLoadRepositoriesClick(INVALID_USERNAME);

        verify(mMockedMainView).showMessage(R.string.MSG_INVALID_USERNAME);
    }

    @After
    public void tearDown() {
        mMainPresenter.onDetach();
    }
}
