package com.liwanag.practice.domain.model.event;

public sealed interface Event permits AnswerEvaluatedEvent, SessionFinishedEvent {
}
