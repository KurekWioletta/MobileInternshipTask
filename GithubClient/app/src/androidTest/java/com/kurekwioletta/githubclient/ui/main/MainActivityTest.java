package com.kurekwioletta.githubclient.ui.main;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.TestApp;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.di.activity.components.MainActivityComponent;
import com.kurekwioletta.githubclient.di.activity.modules.MainActivityModule;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;
import com.kurekwioletta.githubclient.utils.TestConstants;
import com.kurekwioletta.githubclient.utils.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int RESPONSE_STATUS_CODE_SUCCESS = 200;
    private static final int RESPONSE_STATUS_CODE_NOT_FOUND = 404;
    private static final int RESPONSE_STATUS_CODE_UNKNOWN = 520;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public IntentsTestRule<MainActivity> mMainActivityRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Mock
    MainActivityComponent.Builder mMockedBuilder;

    @Mock
    GithubApiManager mMockedGithubApiManager;

    @Mock
    Validator mMockedValidator;

    private MainActivityComponent mMainActivityComponent = new MainActivityComponent() {
        @Override
        public void injectMembers(MainActivity mainActivity) {
            mainActivity.mainPresenter = new MainPresenter<>(
                    mMockedGithubApiManager,
                    new CompositeDisposable(),
                    mMockedValidator);
        }
    };

    @Before
    public void setUp() {
        when(mMockedBuilder.build()).thenReturn(mMainActivityComponent);
        when(mMockedBuilder.activityModule(any(MainActivityModule.class)))
                .thenReturn(mMockedBuilder);

        TestApp app = ApplicationProvider.getApplicationContext();
        app.putActivityComponentBuilder(mMockedBuilder, MainActivity.class);

        mMainActivityRule.launchActivity(new Intent());
    }

    @Test
    public void when_userExists_openRepositoriesListActivity() {

        // arrange - response success user exists
        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getSuccessResponse()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        onView(withId(R.id.et_main_username)).perform(replaceText(TestConstants.VALID_USERNAME));
        onView(withId(R.id.btn_main_load_repositories)).perform(click());

        // assert
        onView(withId(R.id.et_main_username)).check(doesNotExist());
        intended(hasComponent(RepositoriesListActivity.class.getName()));
    }

    @Test
    public void when_usernameIsInvalid_showToastWithInvalidUsernameMessage() {

        // arrange - invalid username
        String expectedMessage = getApplicationContext()
                .getResources()
                .getString(R.string.MSG_INVALID_USERNAME);

        when(mMockedValidator.isUsernameValid(TestConstants.INVALID_USERNAME))
                .thenReturn(false);

        // act
        onView(withId(R.id.et_main_username)).perform(replaceText(TestConstants.INVALID_USERNAME));
        onView(withId(R.id.btn_main_load_repositories)).perform(click());

        // assert
        onView(withText(expectedMessage))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText(expectedMessage)).perform(pressBack());
        onView(withId(R.id.et_main_username)).check(matches(isDisplayed()));
    }

    @Test
    public void when_userWasNotFound_showToastWithUserWasNotFoundMessage() {

        // arrange - response error user was not found
        String expectedMessage = getApplicationContext()
                .getResources()
                .getString(R.string.ERR_NOT_FOUND);

        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getErrorResponseNotFound()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        onView(withId(R.id.et_main_username)).perform(replaceText(TestConstants.VALID_USERNAME));
        onView(withId(R.id.btn_main_load_repositories)).perform(click());

        // assert
        onView(withText(expectedMessage))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText(expectedMessage)).perform(pressBack());
        onView(withId(R.id.et_main_username)).check(matches(isDisplayed()));
    }


    @Test
    public void when_unknownErrorHasOccurred_showUnknownErrorMessage() {

        // arrange - response error unknown
        String expectedMessage = getApplicationContext()
                .getResources()
                .getString(R.string.ERR_UNKNOWN);

        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(getErrorResponseUnknown()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        onView(withId(R.id.et_main_username)).perform(replaceText(TestConstants.VALID_USERNAME));
        onView(withId(R.id.btn_main_load_repositories)).perform(click());

        // assert
        onView(withText(expectedMessage))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText(expectedMessage)).perform(pressBack());
        onView(withId(R.id.et_main_username)).check(matches(isDisplayed()));
    }

    @Test
    public void when_networkErrorHasOccurred_showNetworkErrorMessage() {

        // arrange - network error
        String expectedMessage = getApplicationContext()
                .getResources()
                .getString(R.string.ERR_NETWORK);

        when(mMockedGithubApiManager.getUserResponse(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        when(mMockedValidator.isUsernameValid(TestConstants.VALID_USERNAME))
                .thenReturn(true);

        // act
        onView(withId(R.id.et_main_username)).perform(replaceText(TestConstants.VALID_USERNAME));
        onView(withId(R.id.btn_main_load_repositories)).perform(click());

        // assert
        onView(withText(expectedMessage))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText(expectedMessage)).perform(pressBack());
        onView(withId(R.id.et_main_username)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        mMainActivityRule.finishActivity();
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
