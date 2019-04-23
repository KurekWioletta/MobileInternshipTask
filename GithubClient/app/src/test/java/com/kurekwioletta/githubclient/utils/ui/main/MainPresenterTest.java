package com.kurekwioletta.githubclient.utils.ui.main;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.ui.main.MainContract;
import com.kurekwioletta.githubclient.ui.main.MainPresenter;
import com.kurekwioletta.githubclient.utils.Validator;
import com.kurekwioletta.githubclient.utils.utils.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private static final int RESPONSE_STATUS_CODE_SUCCESS = 200;
    private static final int RESPONSE_STATUS_CODE_NOT_FOUND = 404;
    private static final int RESPONSE_STATUS_CODE_UNKNOWN = 520;

    private MainPresenter<MainContract.View> mMainPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private MainContract.View mMockedMainView;

    @Mock
    private GithubApiManager mMockedGithubApiManager;

    @Mock
    private CompositeDisposable mMockedCompositeDisposable;

    @Mock
    private Validator mMockedValidator;

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter<>(
                mMockedGithubApiManager,
                mMockedCompositeDisposable,
                mMockedValidator);

        mMainPresenter.onAttach(mMockedMainView);
    }

    @Test
    public void when_userExists_openRepositoriesListActivity() {

        // arrange - response success user exists
        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getSuccessResponse()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        mMainPresenter.onLoadRepositoriesClick(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).openRepositoriesListActivity(TestConstants.VALID_USERNAME);
    }

    @Test
    public void when_usernameIsInvalid_showInvalidUsernameMessage() {

        // arrange - invalid username
        when(mMockedValidator.isUsernameValid(TestConstants.INVALID_USERNAME))
                .thenReturn(false);

        // act
        mMainPresenter.onLoadRepositoriesClick(TestConstants.INVALID_USERNAME);

        // assert
        verify(mMockedMainView).showMessage(R.string.MSG_INVALID_USERNAME);
    }

    @Test
    public void when_userWasNotFound_showUserWasNotFoundMessage() {

        // arrange - response error user was not found
        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getErrorResponseNotFound()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        mMainPresenter.onLoadRepositoriesClick(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_NOT_FOUND);
    }

    @Test
    public void when_unknownErrorHasOccurred_showUnknownErrorMessage() {

        // arrange - response error unknown
        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getErrorResponseUnknown()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        mMainPresenter.onLoadRepositoriesClick(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_UNKNOWN);
    }

    @Test
    public void when_networkErrorHasOccurred_showNetworkErrorMessage() {

        // arrange - network error
        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        mMainPresenter.onLoadRepositoriesClick(TestConstants.VALID_USERNAME);

        // assert
        verify(mMockedMainView).showLoading();
        verify(mMockedMainView).hideLoading();
        verify(mMockedMainView).showMessage(R.string.ERR_NETWORK);
    }

    @After
    public void tearDown() {
        mMainPresenter.onDetach();
    }

    private Response<Void> getSuccessResponse() {
        return Response.success(RESPONSE_STATUS_CODE_SUCCESS, null);
    }

    private Response<Void> getErrorResponseNotFound() {
        return Response.error(
                RESPONSE_STATUS_CODE_NOT_FOUND,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "not found"
                ));
    }

    private Response<Void> getErrorResponseUnknown() {
        return Response.error(
                RESPONSE_STATUS_CODE_UNKNOWN,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "unknown"
                ));
    }
}
