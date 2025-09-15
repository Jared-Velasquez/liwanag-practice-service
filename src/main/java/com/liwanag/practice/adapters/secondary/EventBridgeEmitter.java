package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.event.Event;
import com.liwanag.practice.ports.secondary.EventBus;

public class EventBridgeEmitter implements EventBus {
    public void emit(Event event) {
        return;
    }
}
