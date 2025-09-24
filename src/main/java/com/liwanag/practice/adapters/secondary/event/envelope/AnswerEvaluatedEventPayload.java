package com.liwanag.practice.adapters.secondary.event.envelope;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AnswerEvaluatedEventPayload(@NotNull String userId, @NotNull String questionId,
                                          @NotNull String activityId, @NotNull String episodeId,
                                          @NotNull String unitId, @NotNull String result,
                                          @NotNull Instant timestamp) implements EventEnvelope {
}
