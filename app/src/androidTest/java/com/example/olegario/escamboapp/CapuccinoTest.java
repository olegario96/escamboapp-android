package com.example.olegario.escamboapp;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.olegario.escamboapp.activity.HomeWithoutAuthentication;
@RunWith(AndroidJUnit4.class)
public class CapuccinoTest {
    @Rule
    public ActivityTestRule <HomeWithoutAuthentication> mActivityRule =
            new ActivityTestRule<>(HomeWithoutAuthentication.class);
    @Test
    public void capuccinoAutomatedTest() throws InterruptedException, UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.click(704, 109);
        Thread.sleep(1000);
    }
}
