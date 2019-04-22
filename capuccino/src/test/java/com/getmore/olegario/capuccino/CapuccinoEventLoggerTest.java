package com.getmore.olegario.capuccino;

import com.getmore.olegario.capuccino.exception.TextEventBeforeClickEventException;
import com.getmore.olegario.capuccino.model.CapuccinoClickEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEvent;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;
import com.getmore.olegario.capuccino.model.CapuccinoKeyboardEvent;

import org.junit.Test;

import java.util.List;

public class CapuccinoEventLoggerTest {
    private CapuccinoEventLogger capuccinoEventLogger = CapuccinoEventLogger.getInstance();

    @Test
    public void getCapuccinoEvents() {
        CapuccinoClickEvent clickEvent = new CapuccinoClickEvent(22, 19);
        CapuccinoKeyboardEvent keyboardEvent = new CapuccinoKeyboardEvent('g');
        capuccinoEventLogger.addNewCapuccinoEvent(clickEvent);
        capuccinoEventLogger.addNewCapuccinoEvent(keyboardEvent);

        keyboardEvent = new CapuccinoKeyboardEvent('u');
        capuccinoEventLogger.addNewCapuccinoEvent(keyboardEvent);

        final boolean result = capuccinoEventLogger.getCapuccinoEvents().size() == 2;
        assert result;
    }

    @Test
    public void addNewCapuccinoEvent() {
        CapuccinoClickEvent clickEvent = new CapuccinoClickEvent(22, 19);
        CapuccinoKeyboardEvent keyboardEvent = new CapuccinoKeyboardEvent('g');
        capuccinoEventLogger.addNewCapuccinoEvent(clickEvent);
        capuccinoEventLogger.addNewCapuccinoEvent(keyboardEvent);

        keyboardEvent = new CapuccinoKeyboardEvent('u');
        capuccinoEventLogger.addNewCapuccinoEvent(keyboardEvent);
        final List<CapuccinoEvent> capuccinoEventList = capuccinoEventLogger.getCapuccinoEvents();
        final CapuccinoEvent ce = capuccinoEventList.get(capuccinoEventList.size() - 1);
        final boolean result = ((CapuccinoKeyboardEvent) ce).getText().equals("gu");

        assert result;
    }

    @Test
    public void textEventBeforeClickEventException() {
        CapuccinoKeyboardEvent keyboardEvent = new CapuccinoKeyboardEvent('g');
        CapuccinoClickEvent clickEvent = new CapuccinoClickEvent(22, 19);

        try {
            capuccinoEventLogger.addNewCapuccinoEvent(keyboardEvent);
            capuccinoEventLogger.addNewCapuccinoEvent(clickEvent);
        } catch (TextEventBeforeClickEventException e) {
            assert true;
        }
    }
}
