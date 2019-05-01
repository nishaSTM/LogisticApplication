package com.delivery;

import com.delivery.ui.activity.details.LocationActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LocationActivityTest {


    @Rule
    public ActivityTestRule<LocationActivity> mActivityRule =
            new ActivityTestRule<>(LocationActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.

        onView(withId(R.id.desc_location)).perform(click()).check(matches(isDisplayed()));

        // Check that the text was changed.
        onView(withId(R.id.desc_location)).check(matches(withText(R.string.desc)));
    }


}

