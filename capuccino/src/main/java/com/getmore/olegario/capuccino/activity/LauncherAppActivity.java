package com.getmore.olegario.capuccino.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherAppActivity extends AppCompatActivity {
    private String packageName;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        if (packageName == null || className == null)
            throw new ActivityNotFoundException("Package or Classname not set!");

        intent.setComponent(new ComponentName(packageName, className));
        startActivity(intent);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
