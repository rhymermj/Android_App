package com.mjchoi.finalapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NavigationTest {
    @Rule
    public ActivityTestRule<NavigationActivity> activityTestRule = new ActivityTestRule<>(
            NavigationActivity.class);
    @Before
    public void setUp() throws Exception{

    }

    @Test
    public void test(){
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
 //       onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception{

    }
}



