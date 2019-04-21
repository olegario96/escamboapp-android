package com.getmore.olegario.capuccino.model;

import java.util.Date;

public class CapuccinoEvent {
    private Date timestamp;

    public CapuccinoEvent() {
        this.timestamp = new Date();
    }

    public boolean happenedBefore(CapuccinoEvent ce) {
        return this.timestamp.compareTo(ce.getTimestamp()) < 0;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
