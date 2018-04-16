package com.example.absolute.codepasta;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.absolute.codepasta.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

/**
 * Created by Absolute on 12/20/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void editTextUsername_startUserProfileActivity(){

        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();

        // registering MainActivity's idling resource for enabling Espresso sync with MainActivity's
        // background threads


        String exampleUsername = "elenacrst";
        onView(withId(R.id.editTextUsername))
                .perform(typeText(exampleUsername))
                .perform(closeSoftKeyboard());//necessary, elsewhere it will consider it's another
        //app and security exception occurs

        onView(withId(R.id.buttonDoneLogin))
                .perform(click());

        if (mainActivityIdlingResource != null){
            Espresso.registerIdlingResources(mainActivityIdlingResource);
        }

        onView(withId(R.id.username_text)).check(matches(withText(exampleUsername)));
        onView(withId(R.id.username_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.created_at_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.followers_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.following_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.hireable_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.public_repos_text))
                .check(matches(isTextLengthGreater(0)));
        onView(withId(R.id.updated_at_text))
                .check(matches(isTextLengthGreater(0)));
    }

    public static TypeSafeMatcher<View> isTextLengthGreater(final int length) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((TextView) item).getText() != null &&
                        ((TextView) item).getText().toString().length() > length;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isTextInLines");
            }
        };
    }
}

