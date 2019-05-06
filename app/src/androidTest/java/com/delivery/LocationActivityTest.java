package com.delivery;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.test.rule.ActivityTestRule;
import com.delivery.ui.activity.details.LocationActivity;
import org.junit.Rule;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LocationActivityTest {

    @Rule
    public ActivityTestRule<LocationActivity> mActivityRule = new ActivityTestRule<>(
            LocationActivity.class);
    @Rule
    public final CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();


    @Test
    public void clickOnFirstItem_opensComments() throws Throwable {
        drain();
        onView(withContentDescription(R.string.desc))
                .check(matches(not(withText(""))));

    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}