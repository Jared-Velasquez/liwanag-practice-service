package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.adapters.secondary.event.mapper.EventMapper;
import com.liwanag.practice.domain.model.event.Event;
import com.liwanag.practice.ports.secondary.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventBridgeEventBus implements EventBus {
    private final EventBridgeClient eventBridgeClient;
    private final EventMapper eventMapper;
    public void emit(Event event) {
        PutEventsRequestEntry entry = PutEventsRequestEntry.builder()
                .eventBusName("LiwanagEventBus") // Replace with your EventBridge bus name
                .source("scoring.service") // Replace with your source
                .detailType("AnswerEvaluated")
                .detail(eventMapper.toJson(event))
                .build();
        try {
            log.info("Emitting event to EventBridge: {}", eventMapper.toJson(event));
            eventBridgeClient.putEvents(builder -> builder.entries(entry));
        } catch (Exception e) {
            log.error("Failed to emit event to EventBridge", e);
        }
    }
}
