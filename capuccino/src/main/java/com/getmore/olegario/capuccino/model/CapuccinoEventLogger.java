package com.getmore.olegario.capuccino.model;

import java.util.ArrayList;
import java.util.List;

public final class CapuccinoEventLogger {
    private static final CapuccinoEventLogger INSTANCE = new CapuccinoEventLogger();
    private List<CapuccinoEvent> capuccinoEvents;

    private CapuccinoEventLogger() {
        this.capuccinoEvents = new ArrayList<>();
    }

    public static CapuccinoEventLogger getInstance() {
        return INSTANCE;
    }

    public void addNewCapuccinoEvent(CapuccinoEvent ce) {
        if (ce instanceof CapuccinoKeyboardEvent) {
            final CapuccinoEvent lastEvent = this.capuccinoEvents.get(capuccinoEvents.size() - 1);
            if (lastEvent instanceof  CapuccinoKeyboardEvent) {
                final char character = ((CapuccinoKeyboardEvent) ce).getLastCharacter();
                ((CapuccinoKeyboardEvent) lastEvent).concatenate(character);
            } else {
                this.capuccinoEvents.add(ce);
            }
        } else {
            this.capuccinoEvents.add(ce);
        }
    }

    public List<CapuccinoEvent> getCapuccinoEvents() {
        return capuccinoEvents;
    }
}
