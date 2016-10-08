package com.segal_gould.criminalintent;

import android.widget.DatePicker;
import android.widget.TimePicker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.pm.ActivityInfo;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import android.support.test.espresso.contrib.PickerActions;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestApplication {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<CrimeListActivity> mActivityRule = new ActivityTestRule<>(CrimeListActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Noah is born";
    }

    @Test
    public void runThroughTest() {
        // Click the default crime
        onView(withId(R.id.crime_recycler_view)).perform(actionOnItemAtPosition(0, click()));
        // Type in a new Crime Title
        onView(allOf(withId(R.id.crime_title), isDisplayed())).perform(replaceText(mStringToBetyped));
        // Click the Crime Date
        onView(allOf(withId(R.id.crime_date), isDisplayed())).perform(click());
        // Set the Date to my birthday
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1996, 1, 24));
        // Set the Time to my birth-minute
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(4, 1));
        // Hit OK
        onView(withText(containsString("OK"))).perform(click());
        // Go back
        pressBack();
        // Rotate the phone
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Click the default crime
        onView(withId(R.id.crime_recycler_view)).perform(actionOnItemAtPosition(0, click()));
        // Check that the text is the same
        onView(allOf(withId(R.id.crime_date), isDisplayed())).check(matches(withText(containsString("Wed Jan 24 04:01:00 EST 1996"))));
    }

}