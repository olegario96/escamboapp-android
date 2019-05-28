package com.getmore.olegario.capuccino.model;

import android.util.Log;

public class CapuccinoScrollEvent extends CapuccinoClickEvent {
    private int x1;
    private int y1;

    public CapuccinoScrollEvent(int x0, int y0, int x1, int y1) {
        super(x0, y0);
        this.x1 = x1;
        this.y1 = y1;
    }

    public static boolean isACapuccinoScrollEvent(int x0, int y0, int x1, int y1) {
        final int dx = Math.abs(x1 - x0);
        final int dy = Math.abs(y1 - y0);
        return dx > 40 || dy > 40;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    @Override
    public void print() {
        final int x0 = this.getX0();
        final int y0 = this.getY0();
        final int x1 = this.getX1();
        final int y1 = this.getY1();
        final String initialPosition = "(x0: " + x0 + ", y0: " + y0 + ")";
        final String finalPosition = "(x1: " + x1 + ", y1: " + y1 + ")";
        Log.i(">>>CAPUCCINO SCROLL", initialPosition);
        Log.i(">>>CAPUCCINO SCROLL", finalPosition);
    }
}
