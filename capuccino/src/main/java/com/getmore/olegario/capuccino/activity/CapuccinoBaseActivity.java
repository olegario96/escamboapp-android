package com.getmore.olegario.capuccino.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.getmore.olegario.capuccino.model.CapuccinoClickEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEvent;
import com.getmore.olegario.capuccino.model.CapuccinoKeyboardEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;

public class CapuccinoBaseActivity extends AppCompatActivity {

    private CapuccinoEventLogger capuccinoEventLogger;
    private long timestampLastKeyboardEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.timestampLastKeyboardEvent = 0;
        capuccinoEventLogger = CapuccinoEventLogger.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        final int action = me.getActionMasked();
        final int x = (int) me.getX();
        final int y = (int) me.getY();
        CapuccinoClickEvent ce = new CapuccinoClickEvent(x, y);

        if (action == MotionEvent.ACTION_DOWN) {
            this.capuccinoEventLogger.addNewCapuccinoEvent(ce);
        } else if (action == MotionEvent.ACTION_UP) {
            CapuccinoEvent lastCE = this.capuccinoEventLogger.getLastCapuccinoEvent();
            if (lastCE instanceof CapuccinoClickEvent && ce.isHoldEvent(lastCE))
                ((CapuccinoClickEvent) lastCE).setHoldEvent(true);
        }
        return super.dispatchTouchEvent(me);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent kEvent) {
        final int action = kEvent.getAction();
        final int keyCode = kEvent.getKeyCode();
        final char character = (char) kEvent.getUnicodeChar();

        if (action == KeyEvent.ACTION_UP) {
            final long res = kEvent.getEventTime() - this.timestampLastKeyboardEvent;
//            Log.i(">>>TEST",  Long.toString(kEvent.getEventTime()));
//            Log.i(">>>TEST",  Long.toString(res));
            switch (keyCode) {
                // Backspace pressed
                case KeyEvent.KEYCODE_DEL:
                    this.capuccinoEventLogger.removeLastCharacter();
                    break;

                // TAB pressed
                case KeyEvent.KEYCODE_TAB:
                    this.capuccinoEventLogger.markLastCapuccinoEventFinal();
                    break;

                default:
                    // There is a strange behaviour when a special character is typed
                    // the current method is invoked twice, one for the normal key
                    // and the other for the shift + key code.
                    if (kEvent.getEventTime() - this.timestampLastKeyboardEvent != 0) {
                        CapuccinoKeyboardEvent ce = new CapuccinoKeyboardEvent(character);
                        this.capuccinoEventLogger.addNewCapuccinoEvent(ce);
                    }
                    break;
            }
            this.timestampLastKeyboardEvent = kEvent.getEventTime();
        }
        return super.dispatchKeyEvent(kEvent);
    }
}
