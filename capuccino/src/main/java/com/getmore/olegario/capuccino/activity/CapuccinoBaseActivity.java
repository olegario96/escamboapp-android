package com.getmore.olegario.capuccino.activity;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.getmore.olegario.capuccino.R;
import com.getmore.olegario.capuccino.model.CapuccinoClickEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEvent;
import com.getmore.olegario.capuccino.model.CapuccinoKeyboardEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;
import com.getmore.olegario.capuccino.model.CapuccinoOSEvent;
import com.getmore.olegario.capuccino.model.CapuccinoOSEventEnum;
import com.getmore.olegario.capuccino.model.CapuccinoScrollEvent;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class CapuccinoBaseActivity extends AppCompatActivity {
    private KeyboardVisibilityEvent visibilityEvent;
    private boolean isKeyboardVisible;
    private CapuccinoEventLogger capuccinoEventLogger;
    private long timestampLastKeyboardEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.timestampLastKeyboardEvent = 0;
        capuccinoEventLogger = CapuccinoEventLogger.getInstance();
        this.configurateKeyBoardEvent();
        super.onCreate(savedInstanceState);
    }

    private void configurateKeyBoardEvent() {
        this.visibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                isKeyboardVisible = isOpen;
            }
        });
    }

    @Override
    public void onBackPressed() {
        CapuccinoOSEvent capuccinoOSEvent;
        if (this.isKeyboardVisible)
            capuccinoOSEvent = new CapuccinoOSEvent(CapuccinoOSEventEnum.HIDE_KEYBOARD);
        else
            capuccinoOSEvent = new CapuccinoOSEvent(CapuccinoOSEventEnum.BACK_TO_LAST_SCREEN);
        this.capuccinoEventLogger.addNewCapuccinoEvent(capuccinoOSEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        final int action = me.getActionMasked();
        final int x1 = (int) me.getX();
        final int y1 = (int) me.getY();
        CapuccinoClickEvent ce = new CapuccinoClickEvent(x1, y1);
        if (action == MotionEvent.ACTION_DOWN) {
            this.capuccinoEventLogger.addNewCapuccinoEvent(ce);
        } else if (action == MotionEvent.ACTION_UP) {
            CapuccinoEvent lastClickEvent = this.capuccinoEventLogger.getLastCapuccinoEvent();
            if (lastClickEvent instanceof CapuccinoClickEvent) {
                final CapuccinoClickEvent lastCE = (CapuccinoClickEvent) lastClickEvent;
                final int x0 = lastCE.getX0();
                final int y0 = lastCE.getY0();
                if (CapuccinoScrollEvent.isACapuccinoScrollEvent(x0, y0, x1, y1))
                    this.capuccinoEventLogger.addCapuccinoScrollEvent(lastCE, ce);
                else if (ce.isHoldEvent(lastCE))
                    lastCE.setHoldEvent(true);
            }
        }
        return super.dispatchTouchEvent(me);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        switch (me.getAction()){
            case MotionEvent.ACTION_UP:
                return false;
            default:
                return super.onTouchEvent(me);
        }
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent kEvent) {
        final int action = kEvent.getAction();
        final int keyCode = kEvent.getKeyCode();
        final char character = (char) kEvent.getUnicodeChar();
        if (action == KeyEvent.ACTION_UP) {
            final long res = kEvent.getEventTime() - this.timestampLastKeyboardEvent;
            switch (keyCode) {
                case KeyEvent.KEYCODE_TAB:
                    this.capuccinoEventLogger.markLastCapuccinoEventFinal();
                    break;
                case KeyEvent.KEYCODE_ENTER:
                    this.capuccinoEventLogger.removeLastCharacter();
                    return false;
                default:
                    // HACK: there is a strange behaviour when a special character is
                    // typed the current method is invoked twice, one for the normal
                    // key and the other for the shift + key code.
                    if (res != 0) {
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
