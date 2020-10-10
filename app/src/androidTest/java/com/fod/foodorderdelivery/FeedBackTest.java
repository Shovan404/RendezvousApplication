package com.fod.foodorderdelivery;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class FeedBackTest {

    @Rule
    public ActivityTestRule<FeedbackActivity> testRule = new ActivityTestRule<>(FeedbackActivity.class);

    @Test
    public void testFeedback() {
        onView(withId(R.id.etFeedbackEmail)).perform(typeText("feedback@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etFeedbackMsg)).perform(typeText("feedback"), closeSoftKeyboard());
        onView(withId(R.id.btnFeedback)).perform(click());
    }
}
