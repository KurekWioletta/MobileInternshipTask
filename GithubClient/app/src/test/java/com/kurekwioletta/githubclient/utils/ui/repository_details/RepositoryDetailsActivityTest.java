package com.kurekwioletta.githubclient.utils.ui.repository_details;

import android.content.Intent;
import android.widget.TextView;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;
import com.kurekwioletta.githubclient.utils.AppConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RepositoryDetailsActivityTest {

    private static final String EXTRA_REPOSITORY = "com.kurekwioletta.githubclient.EXTRA_REPOSITORY";

    private Repository mRepository;

    private ActivityController<RepositoryDetailsActivity> mRepositoryDetailsActivityController;

    private RepositoryDetailsActivity mRepositoryDetailsActivity;

    @Before
    public void setUp() {
        mRepository = new Repository();
        mRepositoryDetailsActivityController = Robolectric.buildActivity(RepositoryDetailsActivity.class, getIntentWithRepository()).create().start().resume().visible();
        mRepositoryDetailsActivity = mRepositoryDetailsActivityController.get();
    }

    @Test
    public void when_activityStartedAfterOnRepositoryClick_showRepositoryData() {
        assertNotNull(mRepositoryDetailsActivity);

        // validate shown data
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_name)).getText().toString(),
                mRepository.getName());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_description)).getText().toString(),
                mRepository.getDescription());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_created)).getText().toString(),
                mRepository.getCreatedAt());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_updated)).getText().toString(),
                mRepository.getUpdatedAt());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_pushed)).getText().toString(),
                mRepository.getPushedAt());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_hyperlink)).getText().toString(),
                mRepository.getHtmlUrl());
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_watchers)).getText().toString(),
                String.valueOf(mRepository.getWatchersCount()));
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_stars)).getText().toString(),
                String.valueOf(mRepository.getStargazersCount()));
        assertEquals(
                ((TextView) mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_forks)).getText().toString(),
                String.valueOf(mRepository.getForksCount()));
    }

    @After
    public void tearDown() {
        if (mRepositoryDetailsActivityController != null) {
            mRepositoryDetailsActivityController.pause().stop().destroy();
        }
    }

    private Intent getIntentWithRepository() {
        mRepository.setName("a");
        mRepository.setDescription("b");
        mRepository.setWatchersCount(0);
        mRepository.setStargazersCount(1);
        mRepository.setForksCount(2);
        mRepository.setCreatedAt("0000-00-00");
        mRepository.setUpdatedAt("1111-11-11");
        mRepository.setPushedAt("2222-22-22");
        mRepository.setHtmlUrl("c");

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RepositoryDetailsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, mRepository);

        return intent;
    }
}
