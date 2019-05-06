package com.delivery;


import android.content.Context;
import android.content.Intent;

import com.delivery.ui.activity.DeliveryActivity;
import com.delivery.utils.AppConstants;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;

import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class showcasing some {@link RecyclerViewActions} from Espresso.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeliveryActivityTest {


    @Rule
    public final ActivityTestRule<DeliveryActivity> mActivityRule =
            new ActivityTestRule<>(DeliveryActivity.class);
    private final Context context = App.getInstance();

    @Test
    public void ensureTextChangesWork() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, 1);
        mActivityRule.launchActivity(intent);
        onView(withContentDescription(context.getString(R.string.desc))).perform(click());
        assert (mActivityRule.getActivity().isFinishing());
    }
}

