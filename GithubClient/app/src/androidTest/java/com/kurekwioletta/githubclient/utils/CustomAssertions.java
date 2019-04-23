package com.kurekwioletta.githubclient.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.CoreMatchers;

import java.util.Objects;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;

public class CustomAssertions {

    public static ViewAssertion hasItemCount(int count) {
        return new RecyclerViewItemCountAssertion(count);
    }

    static class RecyclerViewItemCountAssertion implements ViewAssertion {

        private int mCount;

        RecyclerViewItemCountAssertion(int count) {
            mCount = count;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            if (!(view instanceof RecyclerView)) {
                throw new IllegalStateException("The asserted view is not RecyclerView");
            }

            if (((RecyclerView) view).getAdapter() == null) {
                throw new IllegalStateException("No adapter is assigned to RecyclerView");
            }

            assertThat("RecyclerView item count",
                    Objects.requireNonNull(((RecyclerView) view).getAdapter()).getItemCount(),
                    CoreMatchers.equalTo(mCount));
        }
    }
}
