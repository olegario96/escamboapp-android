package com.getmore.olegario.capuccino;

import com.getmore.olegario.capuccino.model.CapuccinoEvent;

import org.junit.Test;

public class CapuccinoEventTest {
    private CapuccinoEvent ce = new CapuccinoEvent();

    @Test
    public void getTimestamp() {
        final boolean result = this.ce.getTimestamp() != null;
        assert result;
    }

    @Test
    public void happenedBefore() {
        CapuccinoEvent ce = new CapuccinoEvent();
        final boolean result = this.ce.happenedBefore(ce);
        assert true;
    }
}
