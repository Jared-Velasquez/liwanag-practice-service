package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.event.Event;
import com.liwanag.practice.ports.secondary.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventBridgeEventBus implements EventBus {
    public void emit(Event event) {
        return;
    }
}
