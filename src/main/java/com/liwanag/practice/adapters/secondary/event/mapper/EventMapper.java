package com.liwanag.practice.adapters.secondary.event.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liwanag.practice.adapters.secondary.event.envelope.AnswerEvaluatedEventPayload;
import com.liwanag.practice.adapters.secondary.event.envelope.EventEnvelope;
import com.liwanag.practice.adapters.secondary.event.envelope.SessionFinishedEventPayload;
import com.liwanag.practice.domain.model.event.AnswerEvaluatedEvent;
import com.liwanag.practice.domain.model.event.Event;
import com.liwanag.practice.domain.model.event.SessionFinishedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventMapper {
    private final ObjectMapper objectMapper;

    public String toJson(Event event) {
        EventEnvelope envelope = switch (event) {
            case AnswerEvaluatedEvent e ->
                new AnswerEvaluatedEventPayload(
                        e.userId().toString(), e.questionId(), e.fqid().getActivityId(),
                        e.fqid().getEpisodeId(), e.fqid().getUnitId(), e.result().toString(),
                        e.timestamp()
                );
            case SessionFinishedEvent e ->
                new SessionFinishedEventPayload(
                        e.userId().toString(), e.sessionId().toString(), e.fqid().getActivityId(),
                        e.fqid().getEpisodeId(), e.fqid().getUnitId(), e.timestamp()
                );
        };

        try {
            return objectMapper.writeValueAsString(envelope);
        } catch (Exception ex) {
            log.error("Failed to serialize event to JSON", ex);
            throw new RuntimeException("Failed to serialize event to JSON", ex);
        }
    }
}
