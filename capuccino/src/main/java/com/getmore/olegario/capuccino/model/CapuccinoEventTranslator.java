package com.getmore.olegario.capuccino.model;

import java.util.List;

public final class CapuccinoEventTranslator {
    private static final CapuccinoEventTranslator INSTANCE = new CapuccinoEventTranslator();

    private CapuccinoEventTranslator() {}

    public String[] translateCapuccinoLogger(CapuccinoEventLogger eventLogger) {
        int i = 0;
        List<CapuccinoEvent> capuccinoEvents = eventLogger.getCapuccinoEvents();
        String[] eventsTranslated = new String[capuccinoEvents.size()];
        for (CapuccinoEvent capuccinoEvent: capuccinoEvents) {
            if(capuccinoEvent instanceof CapuccinoClickEvent) {
                eventsTranslated[i] =
                    this.translateCapuccinoClickEvent((CapuccinoClickEvent) capuccinoEvent);
            } else if (capuccinoEvent instanceof CapuccinoScrollEvent) {
                eventsTranslated[i] =
                    this.translateCapuccinoScrollEvent((CapuccinoScrollEvent) capuccinoEvent);
            } else if (capuccinoEvent instanceof CapuccinoKeyboardEvent) {
                eventsTranslated[i] =
                        this.translateCapuccinoKeyboardEvent((CapuccinoKeyboardEvent) capuccinoEvent);
            }
            ++i;
        }
        return eventsTranslated;
    }

    private String translateCapuccinoClickEvent(CapuccinoClickEvent ce) {
        final String x = Integer.toString(ce.getX0());
        final String y = Integer.toString(ce.getY0());
        return ce.getHoldEvent() ?
                "device.swipe("+x+", "+y+", "+x+", "+y+", 100);" :
                "device.click("+x+", "+y+");";
    }

    private String translateCapuccinoScrollEvent(CapuccinoScrollEvent se) {
        final String x0 = Integer.toString(se.getX0());
        final String y0 = Integer.toString(se.getY0());
        final String x1 = Integer.toString(se.getX1());
        final String y1 = Integer.toString(se.getY1());
        return "device.swipe("+x0+", "+y0+", "+x1+", "+y1+", 30);";
    }

    private String translateCapuccinoKeyboardEvent(CapuccinoKeyboardEvent ke) {
        final String text = ke.getText();
        final String uiObject = "new UiObject(new UiSelector().focused(true)).setText(";
        return uiObject + text + ");";
    }

    public static CapuccinoEventTranslator getInstance() {
        return INSTANCE;
    }
}
