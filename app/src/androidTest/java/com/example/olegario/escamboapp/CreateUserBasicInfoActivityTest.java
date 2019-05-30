package com.example.olegario.escamboapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;

import com.example.olegario.escamboapp.activity.HomeWithoutAuthentication;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateUserBasicInfoActivityTest {

    @Rule
    public ActivityTestRule<HomeWithoutAuthentication> mActivityRule =
            new ActivityTestRule<>(HomeWithoutAuthentication.class);

    @Test
    public void test1() throws InterruptedException, UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.click(49, 114);
        Thread.sleep(1000);
        device.click(278, 406);
        Thread.sleep(1000);
        device.click(300, 218);
        Thread.sleep(1000);
        new UiObject(new UiSelector().focused(true)).setText("Gustavo");
        device.swipe(338, 575, 390,  226, 50);
        Thread.sleep(1000);
        device.swipe(382, 586, 431,  185, 50);
        Thread.sleep(1000);
        device.click(388, 294);
        Thread.sleep(1000);
        new UiObject(new UiSelector().focused(true)).setText("asdfasdf");
        Thread.sleep(1000);
    }

    @Test
    public void test2() throws InterruptedException, UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.click(36, 82);
        Thread.sleep(1000);
        device.click(284, 409);
        Thread.sleep(1000);
        device.click(303, 382);
        Thread.sleep(1000);
        new UiObject(new UiSelector().focused(true)).setText("Olegario");
        device.swipe(418, 453, 393,  41, 50);
        Thread.sleep(1000);
        device.click(445, 346);
        Thread.sleep(1000);
        new UiObject(new UiSelector().focused(true)).setText("09991356940");
        Thread.sleep(1000);
    }
}
