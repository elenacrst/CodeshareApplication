package com.example.absolute.codepasta;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.absolute.codepasta.ui.PostActivity;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PostPastaLinkAppearsTest {
    @Rule
    public ActivityTestRule<PostActivity> mActivityRule = new ActivityTestRule<>(
            PostActivity.class);

    @Test
    public void done_linkAppears(){

        CountingIdlingResource postActivityIdlingResource = mActivityRule.getActivity()
                .getEspressoIdlingResourceForPostActivity();


        onView(withId(R.id.pasta_content_title)).perform(typeText("some title"));
        closeSoftKeyboard();
        onView(withId(R.id.description_edit_text)).perform(typeText("some description"));
        closeSoftKeyboard();
        onView(withId(R.id.donePostButton)).perform(click());

        if (postActivityIdlingResource != null){
            Espresso.registerIdlingResources(postActivityIdlingResource);
        }

        onView(withId(R.id.link_pasta_text)).check(matches(containsLink()));
    }

    public static TypeSafeMatcher<View> containsLink() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((TextView) item).getText() != null &&
                        ((TextView)item).getText().toString().contains("https://");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("contains link");
            }
        };
    }
}
