package com.getmore.olegario.capuccino.model;

public class CapuccinoKeyboardEvent extends CapuccinoEvent {
    private StringBuilder sb;

    public CapuccinoKeyboardEvent(char character) {
        this.sb = new StringBuilder();
        sb.append(character);
    }

    public String getText() {
        return sb.toString();
    }

    public void concatenate(char character) {
        this.sb.append(character);
    }

    public char getLastCharacter() {
        final String string = sb.toString();
        return string.charAt(string.length() - 1);
    }
}
