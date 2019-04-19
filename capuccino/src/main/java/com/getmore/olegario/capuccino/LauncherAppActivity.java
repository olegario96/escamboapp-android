package com.getmore.olegario.capuccino;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.os.Debug;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class LauncherAppActivity extends AppCompatActivity {

    private final int REQUEST_CODE_TRACK = 1;
    private String packageName = "com.example.olegario.escamboapp";
    private String className = "com.example.olegario.escamboapp.activity.HomeWithoutAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_app_activity);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(packageName, className));

        startActivityForResult(intent, REQUEST_CODE_TRACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        Log.i(">>>INFO", "HERE");
        dispatchTouchEvent(me);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
