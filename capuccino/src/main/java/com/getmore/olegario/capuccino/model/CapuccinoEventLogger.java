package com.getmore.olegario.capuccino.model;

import android.util.Log;

import com.getmore.olegario.capuccino.exception.TextEventBeforeClickEventException;

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
        if (ce instanceof CapuccinoKeyboardEvent && capuccinoEvents.size() == 0) {
            final String errMsg = "Impossible to perform a keyboard event before a click event";
            throw new TextEventBeforeClickEventException(errMsg);
        }

        if (ce instanceof CapuccinoKeyboardEvent) {
            this.addCapuccinoKeyboardEvent((CapuccinoKeyboardEvent) ce);
        } else {
            this.capuccinoEvents.add(ce);
        }
    }

    public void addCapuccinoKeyboardEvent(CapuccinoKeyboardEvent ce) {
        final CapuccinoEvent lastEvent = this.capuccinoEvents.get(capuccinoEvents.size() - 1);
        if (lastEvent instanceof CapuccinoKeyboardEvent &&
            !((CapuccinoKeyboardEvent) lastEvent).isFinal()) {

            final char character = ((CapuccinoKeyboardEvent) ce).getLastCharacter();
            ((CapuccinoKeyboardEvent) lastEvent).concatenate(character);
        } else {
            this.capuccinoEvents.add(ce);
        }
    }

    public void addCapuccinoScrollEvent(CapuccinoClickEvent startCE, CapuccinoClickEvent finalCE) {
        this.capuccinoEvents.remove(this.capuccinoEvents.size()-1);
        final int x0 = startCE.getX0();
        final int y0 = startCE.getY0();
        final int x1 = finalCE.getX0();
        final int y1 = finalCE.getY0();
        final CapuccinoScrollEvent capuccinoScrollEvent = new CapuccinoScrollEvent(x0, y0, x1, y1);
        this.capuccinoEvents.add(capuccinoScrollEvent);
    }

    public void markLastCapuccinoEventFinal() {
        CapuccinoEvent ce = this.getLastCapuccinoEvent();
        if (ce instanceof CapuccinoKeyboardEvent)
            ((CapuccinoKeyboardEvent) ce).setFinal(true);
    }

    public void removeLastCharacter() {
        CapuccinoEvent ce = this.getLastCapuccinoEvent();
        if (ce instanceof CapuccinoKeyboardEvent)
            ((CapuccinoKeyboardEvent) ce).removeLastCharacter();
    }

    public List<CapuccinoEvent> getCapuccinoEvents() {
        return capuccinoEvents;
    }

    public CapuccinoEvent getLastCapuccinoEvent() {
        return this.capuccinoEvents.get(this.capuccinoEvents.size() - 1);
    }

    public void print() {
        for (CapuccinoEvent capuccinoEvent: this.capuccinoEvents) {
            capuccinoEvent.print();
        }
    }
}
