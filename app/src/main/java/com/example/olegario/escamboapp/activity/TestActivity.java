package com.example.olegario.escamboapp.activity;

import android.os.Bundle;

import com.getmore.olegario.capuccino.activity.LauncherAppActivity;

public class TestActivity extends LauncherAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String packageName = "com.example.olegario.escamboapp";
        final String className = packageName + ".activity.HomeWithoutAuthentication";
        this.setPackageName(packageName);
        this.setClassName(className);

        super.onCreate(savedInstanceState);
    }
}
