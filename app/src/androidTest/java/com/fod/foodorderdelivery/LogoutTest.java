package com.fod.foodorderdelivery;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LogoutTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loadFragment() {
        testRule.getActivity().getFragmentManager().beginTransaction();
    }


    @Test
    public void testLogout() {
        onView(withId(R.id.imgUserProfile)).perform(click());
        onView(withId(R.id.etEmail)).perform(typeText("shovan@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("shovan1234"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.imgUserProfile)).perform(click());
        onView(withText("Logout")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
    }
}
