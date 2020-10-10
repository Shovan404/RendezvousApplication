package com.fod.foodorderdelivery;

import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class SearchTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loadFragment() {
        testRule.getActivity().getFragmentManager().beginTransaction();
    }

    @Test
    public void testSearch() {
        onView(withId(R.id.navSearch)).perform(click());
        onView(withId(R.id.searchView)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("burger"));
        onView(withId(R.id.searchView)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
    }

}
