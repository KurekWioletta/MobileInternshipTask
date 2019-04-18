package com.kurekwioletta.githubclient.utils.ui.repositories_list;

import android.app.Application;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;
import com.kurekwioletta.githubclient.utils.utils.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.test.core.app.ApplicationProvider;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class RepositoriesListActivityTest {

    private static final String EXTRA_USERNAME = "com.kurekwioletta.githubclient.EXTRA_USERNAME";

    private ActivityController<RepositoriesListActivity> mRepositoriesListActivityController;

    private RepositoriesListActivity mRepositoriesListActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mRepositoriesListActivityController = Robolectric.buildActivity(RepositoriesListActivity.class, getIntentWithUsername()).create().start().resume().visible();
        mRepositoriesListActivity = mRepositoriesListActivityController.get();
    }

    @Test
    public void when_downloadedRepositories_showRepositoriesList() {
        assertNotNull(mRepositoriesListActivity);

        List<Repository> repositoriesList = getRepositoriesList();
        RecyclerView rvRepositoriesList = mRepositoriesListActivity.findViewById(R.id.rv_repositories_list);

        mRepositoriesListActivity.updateRepositoriesListRecyclerView(repositoriesList);

        // validate shown data
        assertEquals(
                Objects.requireNonNull(rvRepositoriesList.getAdapter()).getItemCount(),
                repositoriesList.size());
    }

    @Test
    public void when_onRepositoryClick_startRepositoryDetailsActivity() {
        assertNotNull(mRepositoriesListActivity);

        mRepositoriesListActivity.onRepositoryClicked(new Repository());

        Intent expectedIntent = new Intent(mRepositoriesListActivity, RepositoryDetailsActivity.class);
        Intent startedIntent = shadowOf((Application) getApplicationContext()).getNextStartedActivity();

        assertNotNull(startedIntent);
        assertEquals(expectedIntent.getComponent(), startedIntent.getComponent());
    }

    @After
    public void tearDown() {
        if (mRepositoriesListActivityController != null) {
            mRepositoriesListActivityController.pause().stop().destroy();
        }
    }

    private Intent getIntentWithUsername() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RepositoryDetailsActivity.class);
        intent.putExtra(EXTRA_USERNAME, TestConstants.VALID_USERNAME);

        return intent;
    }

    private List<Repository> getRepositoriesList() {
        List<Repository> repositoriesList = new ArrayList<>();
        Repository repository = new Repository();

        repository.setName("a");
        repository.setDescription("b");
        repository.setWatchersCount(0);
        repository.setStargazersCount(1);
        repository.setForksCount(2);
        repository.setCreatedAt("0000-00-00");
        repository.setUpdatedAt("1111-11-11");
        repository.setPushedAt("2222-22-22");
        repository.setHtmlUrl("c");

        for (int i = 0; i < 5; i++) {
            repositoriesList.add(repository);
        }

        return repositoriesList;
    }
}
