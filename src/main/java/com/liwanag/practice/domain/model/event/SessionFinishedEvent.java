package com.liwanag.practice.domain.model.event;

import com.liwanag.practice.domain.model.content.FqId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SessionFinishedEvent(@NotNull UUID userId, @NotNull UUID sessionId, @NotNull FqId fqid,
                                   @NotNull Instant timestamp) implements Event {
    @Override
    public DetailType detailType() {
        return DetailType.SESSION_FINISHED;
    }
}
