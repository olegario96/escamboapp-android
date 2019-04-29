package com.getmore.olegario.capuccino.model;

import android.util.Log;

public class CapuccinoClickEvent extends CapuccinoEvent {
    private int x;
    private int y;
    private boolean holdEvent;

    public CapuccinoClickEvent(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.holdEvent = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getHoldEvent() {
        return holdEvent;
    }

    public void setHoldEvent(boolean holdEvent) {
        this.holdEvent = holdEvent;
    }

    public boolean isHoldEvent(CapuccinoEvent ce) {
        final long timeFromThisEvent = this.getTimestamp().getTime();
        final long timeFromParamEvent = ce.getTimestamp().getTime();
        // If someone is seeing this code line, please I'm sorry
        // The division is totally opinion based and it uses magic numbers
        final double diffSeconds = (timeFromThisEvent - timeFromParamEvent) / 500;
        return diffSeconds > 1;
    }

    @Override
    public void print() {
        final int x = this.getX();
        final int y = this.getY();
        String holdEvent = "";
        if (getHoldEvent())
            holdEvent += " (Is a click hold event)";
        else
            holdEvent += " (Is not a click hold event)";

        final String text = "(" + x + ", " + y + ")" + holdEvent;
        Log.i(">>>CAPUCCINO CLICK", text);
    }
}
