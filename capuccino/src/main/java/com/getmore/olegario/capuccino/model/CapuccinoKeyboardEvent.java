package com.getmore.olegario.capuccino.model;

import android.util.Log;

public class CapuccinoKeyboardEvent extends CapuccinoEvent {
    private StringBuilder sb;
    private boolean isFinal;

    public CapuccinoKeyboardEvent(char character) {
        this.sb = new StringBuilder();
        sb.append(character);
        this.isFinal = false;
    }

    public String getText() {
        return sb.toString();
    }

    public void concatenate(char character) {
        if (!this.isFinal)
            this.sb.append(character);
    }

    public void removeLastCharacter() {
        if (!this.isFinal && this.sb.length() > 0)
            this.sb.deleteCharAt(this.sb.length() - 1);
    }

    public char getLastCharacter() {
        final String string = sb.toString();
        return string.charAt(string.length() - 1);
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public void print() {
        final String text = this.getText();
        Log.i(">>>CAPUCCINO TEXT", text);
    }
}
