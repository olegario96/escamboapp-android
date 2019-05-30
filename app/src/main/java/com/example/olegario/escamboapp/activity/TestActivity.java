package com.example.olegario.escamboapp.activity;

import android.os.Bundle;

import com.getmore.olegario.capuccino.activity.LauncherAppActivity;

public class TestActivity extends LauncherAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String packagePath = "com.example.olegario.escamboapp";
        final String activityPackagePath = "activity";
        final String className = "HomeWithoutAuthentication";
        final String testFileName = "CapuccinoTest";
        final String expectedAssertion = "assertion test";
        this.setPackagePath(packagePath);
        this.setActiviyPackagePath(activityPackagePath);
        this.setClassName(className);
        this.setTestFileName(testFileName);
        this.setExpectedAssertion(expectedAssertion);
        super.onCreate(savedInstanceState);
    }
}
