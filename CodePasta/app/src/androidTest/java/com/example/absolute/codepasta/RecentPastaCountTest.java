package com.example.absolute.codepasta;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.absolute.codepasta.ui.UserProfileActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

/**
 * Created by Absolute on 12/21/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecentPastaCountTest {
    @Rule
    public ActivityTestRule<UserProfileActivity> mActivityRule = new ActivityTestRule<>(
            UserProfileActivity.class);

    @Test
    public void recyclerView_countRecentTest(){

        onView(withId(R.id.recentPastaButton))
                .perform(click());

        CountingIdlingResource profileActivityIdlingResource = UserProfileActivity.getEspressoIdlingResourceForProfileActivity();
        if (profileActivityIdlingResource != null){
            Espresso.registerIdlingResources(profileActivityIdlingResource);
        }

        // registering MainActivity's idling resource for enabling Espresso sync with MainActivity's
        // background threads

        onView(withId(R.id.recyclerview_repos)).check(new RecyclerViewItemCountAssertion(
                20));

    }
    public class RecyclerViewItemCountAssertion implements ViewAssertion {//custom view assertion,
        // used in check
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }
}
