package com.getmore.olegario.capuccino;

import com.getmore.olegario.capuccino.model.CapuccinoClickEvent;

import org.junit.Test;

public class CapuccinoClickEventTest {

    private CapuccinoClickEvent ce = new CapuccinoClickEvent(43, 55);

    @Test
    public void getX() {
        final boolean result = ce.getX0() == 43;
        assert result;
    }

    @Test
    public void getY() {
        final boolean result = ce.getY0() == 55;
        assert result;
    }
}
