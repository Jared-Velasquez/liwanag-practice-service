package com.liwanag.practice.domain.model.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public final class SessionFinishedEvent implements Event {
    @NotNull
    private final UUID userId;
    @NotNull
    private final UUID sessionId;
    @NotNull
    private final Instant timestamp;
}
