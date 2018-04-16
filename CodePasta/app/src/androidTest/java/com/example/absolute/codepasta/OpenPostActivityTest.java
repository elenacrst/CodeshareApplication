package com.example.absolute.codepasta;


import android.support.test.rule.ActivityTestRule;

import com.example.absolute.codepasta.ui.UserProfileActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class OpenPostActivityTest {
    @Rule
    public ActivityTestRule<UserProfileActivity> mActivityRule = new ActivityTestRule<>(
            UserProfileActivity.class);

    @Test
    public void postButton_opensPostActivity(){
        onView(withId(R.id.postNewPastaButton)).perform(click());
        onView(withId(R.id.pasta_content_title)).check(matches(isDisplayed()));
    }
}
