package com.liwanag.practice.adapters.secondary.event.envelope;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record SessionFinishedEventPayload(@NotNull String userId, @NotNull String sessionId,
                                          @NotNull String activityId, @NotNull String episodeId,
                                          @NotNull String unitId,
                                          @NotNull Instant timestamp) implements EventEnvelope {
}
