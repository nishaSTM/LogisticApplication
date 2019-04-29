package com.delivery;

import com.delivery.ui.activity.main.DeliveryActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DeliveryActivityTest {


    @Rule
    public ActivityTestRule<DeliveryActivity> mActivityRule =
            new ActivityTestRule<>(DeliveryActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.

        onView(withId(R.id.desc)).perform(click()).check(matches(isDisplayed()));

        // Check that the text was changed.
        onView(withId(R.id.desc)).check(matches(withText(R.string.desc)));

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.

        onView(withId(R.id.image)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.desc
        )).check(matches(withText(R.string.desc)));
    }


}

