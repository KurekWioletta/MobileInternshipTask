package com.kurekwioletta.githubclient.utils.ui.main;

import android.app.Application;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.ui.main.MainActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private static final String VALID_USERNAME = "KurekWioletta";

    private MainActivity mActivity;

    private ActivityController<MainActivity> mMainActivityController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mMainActivityController = Robolectric.buildActivity(MainActivity.class).create().start().resume().visible();
        mActivity = mMainActivityController.get();
    }

    @Test
    public void when_givenInvalidUsernameOrNetworkError_repositoryListActivityNotOpened() {
        assertNotNull(mActivity);

        mActivity.findViewById(R.id.btn_main_load_repositories).performClick();

        Intent startedIntent = shadowOf((Application) getApplicationContext()).getNextStartedActivity();

        assertNull(startedIntent);
    }

    @Test
    public void when_givenValidUsername_openRepositoryListActivity() {
        assertNotNull(mActivity);

        mActivity.openRepositoriesListActivity(VALID_USERNAME);

        Intent expectedIntent = new Intent(mActivity, RepositoriesListActivity.class);
        Intent startedIntent = shadowOf((Application) getApplicationContext()).getNextStartedActivity();

        assertNotNull(startedIntent);
        assertEquals(expectedIntent.getComponent(), startedIntent.getComponent());
    }

    @After
    public void tearDown() {
        if (mMainActivityController != null) {
            mMainActivityController.pause().stop().destroy();
        }
    }

}