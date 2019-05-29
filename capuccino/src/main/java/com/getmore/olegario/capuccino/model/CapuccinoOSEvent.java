package com.getmore.olegario.capuccino.model;

import android.util.Log;

public class CapuccinoOSEvent extends CapuccinoEvent {
    private CapuccinoOSEventEnum osEvent;

    public CapuccinoOSEvent(CapuccinoOSEventEnum osEvent) {
        this.osEvent = osEvent;
    }

    @Override
    public void print() {
        Log.i(">>>CAPUCCINO OS EVENT", this.osEvent.toString());
    }
}
