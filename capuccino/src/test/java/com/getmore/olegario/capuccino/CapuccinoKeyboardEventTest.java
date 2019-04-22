package com.getmore.olegario.capuccino;

import com.getmore.olegario.capuccino.model.CapuccinoKeyboardEvent;

import org.junit.Test;


public class CapuccinoKeyboardEventTest {
    private CapuccinoKeyboardEvent keyboardEvent = new CapuccinoKeyboardEvent('a');

    @Test
    public void getText() {
        final boolean result = keyboardEvent.getText().equals("a");
        assert result;
    }

    @Test
    public void concatenate() {
        keyboardEvent.concatenate('b');
        final boolean result = keyboardEvent.getText().equals("ab");
        assert result;
    }

    @Test
    public void getLastCharacter() {
        final char lastCharacter = keyboardEvent.getLastCharacter();
        final boolean result = lastCharacter == 'a';
        assert result;
    }
}
