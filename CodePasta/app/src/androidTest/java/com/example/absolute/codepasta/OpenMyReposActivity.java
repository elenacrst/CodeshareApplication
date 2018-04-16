package com.example.absolute.codepasta;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.absolute.codepasta.ui.MainActivity;
import com.example.absolute.codepasta.ui.UserProfileActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Absolute on 12/28/2017.
 */

@RunWith(AndroidJUnit4.class)
public class OpenMyReposActivity {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void myRepos_opensReposActivity(){

        String usernameExample = "elenacrst";
        onView(withId(R.id.editTextUsername)).perform(typeText(usernameExample));
        closeSoftKeyboard();
        onView(withId(R.id.buttonDoneLogin)).perform(click());

        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();
        if (mainActivityIdlingResource != null){
            Espresso.registerIdlingResources(mainActivityIdlingResource);
        }

        onView(withId(R.id.userPastasButton)).perform(click());

        CountingIdlingResource profileActivityIdlingResource = UserProfileActivity.getEspressoIdlingResourceForProfileActivity();
        //IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.MINUTES);//to avoid idling res timeout exception
       // IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.MINUTES);
        if (profileActivityIdlingResource != null){
            Espresso.registerIdlingResources(profileActivityIdlingResource);
        }

        onView(withId(R.id.recyclerview_repos)).check(matches(isDisplayed()));
    }
}
