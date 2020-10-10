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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class ChangePasswordTest {
    @Rule
    public ActivityTestRule<ChangepasswordActivity> testRule = new ActivityTestRule<>(ChangepasswordActivity.class);

    @Test
    public void testChangePassword() {
        onView(withId(R.id.etOldPassword)).perform(typeText("shovan123456789"), closeSoftKeyboard());
        onView(withId(R.id.etChangePassword)).perform(typeText("shovan1234"), closeSoftKeyboard());
        onView(withId(R.id.etReChangePassword)).perform(typeText("shovan1234"), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
        onView(withId(R.id.btnChangePassword)).check(matches(isDisplayed()));

    }
}
