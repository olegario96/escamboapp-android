package com.getmore.olegario.capuccino.model;

public class CapuccinoClickEvent extends CapuccinoEvent {
    private int x;
    private int y;

    public CapuccinoClickEvent(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
