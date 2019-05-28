package com.example.olegario.escamboapp.activity;

import android.os.Bundle;

import com.getmore.olegario.capuccino.activity.LauncherAppActivity;

public class TestActivity extends LauncherAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String packageName = "com.example.olegario.escamboapp";
        final String className = packageName + ".activity.HomeWithoutAuthentication";
        final String testFileName = "CapuccinoTest";
        final String expectedAssertion = "assertion test";
        this.setPackageName(packageName);
        this.setClassName(className);
        this.setTestFileName(testFileName);
        this.setExpectedAssertion(expectedAssertion);
        super.onCreate(savedInstanceState);
    }
}
