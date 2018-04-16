package com.example.absolute.codepasta;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;

import com.example.absolute.codepasta.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Created by Absolute on 12/20/2017.
 */

@RunWith(AndroidJUnit4.class)
public class EmptyLoginText {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void editTextUsername_startUserProfileActivity(){

        onView(withId(R.id.editTextUsername))
                .perform(clearText());
        closeSoftKeyboard();
        onView(withId(R.id.buttonDoneLogin))
                .perform(click());

        final Activity activity = mActivityRule.getActivity();
        final EditText mUsernameEditText = (EditText) activity.findViewById(R.id.editTextUsername);
        @ColorInt int hintColor = mUsernameEditText.getCurrentHintTextColor();
        @ColorInt int errorColor =ContextCompat.getColor(mUsernameEditText.getContext(),
                R.color.colorAccent) ;
        assertEquals(hintColor, errorColor);
    }

}
