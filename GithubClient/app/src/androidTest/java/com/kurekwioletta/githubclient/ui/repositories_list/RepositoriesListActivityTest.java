package com.kurekwioletta.githubclient.ui.repositories_list;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.google.gson.GsonBuilder;
import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.TestApp;
import com.kurekwioletta.githubclient.data.GithubApiManager;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.di.activity.components.RepositoriesListActivityComponent;
import com.kurekwioletta.githubclient.di.activity.modules.RepositoriesListActivityModule;
import com.kurekwioletta.githubclient.ui.repositories_list.adapter.RepositoriesListAdapter;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;
import com.kurekwioletta.githubclient.utils.TestConstants;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.kurekwioletta.githubclient.utils.CustomAssertions.hasItemCount;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RepositoriesListActivityTest {

    private static final String EXTRA_USERNAME = "com.kurekwioletta.githubclient.EXTRA_USERNAME";

    private List<Repository> mRepositoriesList;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public IntentsTestRule<RepositoriesListActivity> mRepositoriesListActivityRule =
            new IntentsTestRule<>(RepositoriesListActivity.class, true, false);

    @Mock
    RepositoriesListActivityComponent.Builder mMockedBuilder;

    @Mock
    GithubApiManager mMockedGithubApiManager;

    private RepositoriesListActivityComponent mRepositoriesListActivityComponent =
            new RepositoriesListActivityComponent() {
                @Override
                public void injectMembers(RepositoriesListActivity repositoriesListActivity) {
                    repositoriesListActivity.repositoriesListPresenter = new RepositoriesListPresenter<>(
                            mMockedGithubApiManager,
                            new CompositeDisposable());

                    repositoriesListActivity.repositoriesListAdapter = new RepositoriesListAdapter(
                            mRepositoriesListActivityRule.getActivity());
                }
            };

    @Before
    public void setUp() throws IOException {
        mRepositoriesList = getRepositoriesList();

        when(mMockedBuilder.build()).thenReturn(mRepositoriesListActivityComponent);
        when(mMockedBuilder.activityModule(any(RepositoriesListActivityModule.class)))
                .thenReturn(mMockedBuilder);

        TestApp app = ApplicationProvider.getApplicationContext();
        app.putActivityComponentBuilder(mMockedBuilder, RepositoriesListActivity.class);
    }

    @Test
    public void when_repositoriesListWasLoadedSuccessfully_updateRepositoriesList() {

        // arrange - successfully loaded repositories
        when(mMockedGithubApiManager.getUsersRepositoriesList(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(mRepositoriesList));

        // act
        mRepositoriesListActivityRule.launchActivity(
                getIntentWithValidUsername());

        // assert
        onView(withId(R.id.rv_repositories_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_repositories_list)).check(hasItemCount(mRepositoriesList.size()));
    }

    @Test
    public void when_networkErrorHasOccurred_showNetworkErrorMessage() {

        // arrange - network error
        String expectedMessage = getApplicationContext()
                .getResources()
                .getString(R.string.ERR_NETWORK);

        when(mMockedGithubApiManager.getUsersRepositoriesList(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.error(new RuntimeException()));

        // act
        mRepositoriesListActivityRule.launchActivity(
                getIntentWithValidUsername());

        // assert
        onView(withText(expectedMessage))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText(expectedMessage)).perform(pressBack());
        onView(withId(R.id.rv_repositories_list)).check(matches(isDisplayed()));
    }

    @Test
    public void when_repositoryWasClicked_startRepositoryDetailsActivity() {

        // arrange - successfully loaded repository
        when(mMockedGithubApiManager.getUsersRepositoriesList(TestConstants.VALID_USERNAME))
                .thenReturn(Observable.just(mRepositoriesList));

        mRepositoriesListActivityRule.launchActivity(
                getIntentWithValidUsername());

        // act - clicked first repository
        mRepositoriesListActivityRule
                .getActivity()
                .onRepositoryClicked(mRepositoriesList.get(0));

        // assert
        onView(withId(R.id.rv_repositories_list)).check(doesNotExist());
        intended(hasComponent(RepositoryDetailsActivity.class.getName()));
    }

    @After
    public void tearDown() {
        mRepositoriesListActivityRule.finishActivity();
    }

    private Intent getIntentWithValidUsername() {
        return new Intent().putExtra(EXTRA_USERNAME, TestConstants.VALID_USERNAME);
    }

    private List<Repository> getRepositoriesList() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        InputStream inputStream =
                Objects.requireNonNull(getClass()
                        .getClassLoader())
                        .getResourceAsStream("repositoriesList.json");

        return Arrays.asList(gsonBuilder.create()
                .fromJson(IOUtils.toString(inputStream, "UTF-8"), Repository[].class));
    }
}
