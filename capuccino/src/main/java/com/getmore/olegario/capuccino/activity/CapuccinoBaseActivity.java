package com.getmore.olegario.capuccino.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.security.Key;

public class CapuccinoBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        final int action = me.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            final int x = (int) me.getX();
            final int y = (int) me.getY();
            // TODO
        }
        return super.dispatchTouchEvent(me);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent kEvent) {
        final int action = kEvent.getAction();
        if (action == KeyEvent.ACTION_UP) {
            final int keyUnicode = kEvent.getUnicodeChar();
            if (keyUnicode != 0) {
                final char character = (char) keyUnicode;
                // TODO
            }
        }
        return super.dispatchKeyEvent(kEvent);
    }
}
