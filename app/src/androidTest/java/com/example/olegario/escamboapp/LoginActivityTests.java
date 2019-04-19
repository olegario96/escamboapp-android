package com.example.olegario.escamboapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.example.olegario.escamboapp.activity.HomeWithoutAuthentication;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTests {

    @Rule
    public ActivityTestRule<HomeWithoutAuthentication> mActivityRule =
        new ActivityTestRule<>(HomeWithoutAuthentication.class);

    @Test
    public void testLoginWithValidUser() {
        final String email = "gustavo-olegario@hotmail.com";
        final String password = "asdfasdf";
        final String login = getResourceString(R.string.signIn);
        final String welcome = getResourceString(R.string.welcomeUser) + " Gustavo";

        onView(withId(R.id.drawer_layout_without_authentication))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open());


        onView(withText(login)).perform(click());
        onView(withId(R.id.emailSignInEditText))
                .perform(typeText(email), closeSoftKeyboard());

        onView(withId(R.id.passwordLoginEditText))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.signInButton)).perform(click());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText(welcome))
            .check(ViewAssertions.matches(withParent(withId(R.id.toolbarWithAuthentication))));
    }

    @Test
    public void testLogoutWithValidUser() {
        final String appName = getResourceString(R.string.app_name);
        final String logout = getResourceString(R.string.signOut);

        onView(withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());


        onView(withText(logout)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText(appName))
                .check(ViewAssertions.matches(withParent(withId(R.id.toolbar))));
    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }
}
