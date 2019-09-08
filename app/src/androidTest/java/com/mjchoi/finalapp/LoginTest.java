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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

    public class LoginTest {
        @Rule
        public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(
                LoginActivity.class);
        @Before
        public void setUp() throws Exception{

        }
        @Test
        public void displayUI_clickButtons(){
            onView(withId(R.id.email_login)).check(matches(isDisplayed()));
            onView(withId(R.id.password_login)).check(matches(isDisplayed()));
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
            onView(withId(R.id.login_button)).perform(click());
            onView(withId(R.id.sign_up)).check(matches(isDisplayed()));
            onView(withId(R.id.nav_button)).check(matches(isDisplayed()));
            onView(withId(R.id.nav_button)).perform(click());
        }

        @After
        public void tearDown() throws Exception{

        }
    }


