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


public class SignupTest {
    @Rule
    public ActivityTestRule<SignupActivity> testRule = new ActivityTestRule<>(SignupActivity.class);
    @Test
    public void testSignup() {
        onView(withId(R.id.etEmail)).perform(typeText("newaccount@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etName)).perform(typeText("ajar123456"), closeSoftKeyboard());
        onView(withId(R.id.etPhoneNumber)).perform(typeText("11112345"), closeSoftKeyboard());
        onView(withId(R.id.etNewPassword)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.etRepassword)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.btnSignup)).perform(click());
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }
}
