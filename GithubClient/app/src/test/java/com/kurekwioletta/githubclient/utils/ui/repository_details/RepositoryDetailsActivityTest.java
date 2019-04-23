package com.kurekwioletta.githubclient.utils.ui.repository_details;

import android.content.Intent;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import com.google.gson.GsonBuilder;
import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RepositoryDetailsActivityTest {

    private static final String EXTRA_REPOSITORY = "com.kurekwioletta.githubclient.EXTRA_REPOSITORY";

    private Repository mRepository;
    private ActivityController<RepositoryDetailsActivity> mRepositoryDetailsActivityController;
    private RepositoryDetailsActivity mRepositoryDetailsActivity;

    @Before
    public void setUp() throws IOException {
        mRepository = getRepository();
        mRepositoryDetailsActivityController =
                Robolectric.buildActivity(RepositoryDetailsActivity.class, getIntentWithRepository())
                        .create()
                        .start()
                        .resume()
                        .visible();
        mRepositoryDetailsActivity = mRepositoryDetailsActivityController.get();
    }

    @Test
    public void when_activityStarted_activityShouldNotBeNull() {
        assertNotNull(mRepositoryDetailsActivity);
    }

    @Test
    public void when_activityStartedWithIntentExtraRepository_showRepositoryData() {

        // arrange
        TextView tvName = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_name);
        TextView tvDescription = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_description);
        TextView tvCreated = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_created);
        TextView tvUpdated = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_updated);
        TextView tvPushed = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_pushed);
        TextView tvHyperlink = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_hyperlink);
        TextView tvWatchers = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_watchers);
        TextView tvStars = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_stars);
        TextView tvForks = mRepositoryDetailsActivity.findViewById(R.id.tv_repository_details_forks);

        // assert
        assertEquals(tvName.getText().toString(), mRepository.getName());
        assertEquals(tvDescription.getText().toString(), mRepository.getDescription());
        assertEquals(tvCreated.getText().toString(), mRepository.getCreatedAt().substring(0, 10));
        assertEquals(tvUpdated.getText().toString(), mRepository.getUpdatedAt().substring(0, 10));
        assertEquals(tvPushed.getText().toString(), mRepository.getPushedAt().substring(0, 10));
        assertEquals(tvHyperlink.getText().toString(), mRepository.getHtmlUrl());

        assertEquals(tvWatchers.getText().toString(), String.valueOf(mRepository.getWatchersCount()));
        assertEquals(tvStars.getText().toString(), String.valueOf(mRepository.getStargazersCount()));
        assertEquals(tvForks.getText().toString(), String.valueOf(mRepository.getForksCount()));
    }

    @After
    public void tearDown() {
        if (mRepositoryDetailsActivityController != null) {
            mRepositoryDetailsActivityController
                    .pause()
                    .stop()
                    .destroy();
        }
    }

    private Intent getIntentWithRepository() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RepositoryDetailsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, mRepository);

        return intent;
    }

    private Repository getRepository() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        InputStream inputStream =
                Objects.requireNonNull(getClass()
                        .getClassLoader())
                        .getResourceAsStream("repository.json");

        return gsonBuilder.create().fromJson(
                IOUtils.toString(inputStream, "UTF-8"),
                Repository.class);
    }
}
