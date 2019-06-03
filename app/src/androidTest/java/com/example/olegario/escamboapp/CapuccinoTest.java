package com.example.olegario.escamboapp;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
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
        device.click(85, 106);
        Thread.sleep(1000);
        device.click(227, 521);
        Thread.sleep(1000);
        device.click(319, 635);
        Thread.sleep(1000);
        new UiObject(new UiSelector().focused(true)).setText("ASDF");
        Thread.sleep(1000);
        device.pressBack();
        Thread.sleep(1000);
        device.click(404, 936);
        Thread.sleep(1000);
        Espresso.onView(
                ViewMatchers.withId(R.id.passwordLoginEditText))
                .check(ViewAssertions.matches(
                        ViewMatchers.hasErrorText("Senha deve conter no mínimo 8 caracteres")
                    )
                );
    }
}
