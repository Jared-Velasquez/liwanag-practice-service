package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.event.Event;

public interface EventBus {
    /**
     * Emit an event to the event bus.
     * @param event
     */
    void emit(Event event);
}
