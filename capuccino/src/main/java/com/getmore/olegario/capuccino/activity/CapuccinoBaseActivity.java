package com.getmore.olegario.capuccino.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.getmore.olegario.capuccino.model.CapuccinoClickEvent;
import com.getmore.olegario.capuccino.model.CapuccinoKeyboardEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;

public class CapuccinoBaseActivity extends AppCompatActivity {

    private CapuccinoEventLogger capuccinoEventLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        capuccinoEventLogger = CapuccinoEventLogger.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        final int action = me.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            final int x = (int) me.getX();
            final int y = (int) me.getY();
            CapuccinoClickEvent ce = new CapuccinoClickEvent(x, y);
            this.capuccinoEventLogger.addNewCapuccinoEvent(ce);
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
                CapuccinoKeyboardEvent ce = new CapuccinoKeyboardEvent(character);
                this.capuccinoEventLogger.addNewCapuccinoEvent(ce);
            }
        }
        return super.dispatchKeyEvent(kEvent);
    }
}
