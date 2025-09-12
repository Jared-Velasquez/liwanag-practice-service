package com.liwanag.practice.domain.model.session;

import com.liwanag.practice.domain.model.content.FqId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Session {
    private UUID sessionId;
    private UUID userId;
    private FqId activityFqId;
    private Integer activityVersion;
    private Status status;

    private String currentQuestion;
    private UUID turnToken;
    private Long leaseExpiresAt;

    private Integer attempted;
    private Integer correct;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;

    public enum Status {
        IDLE,
        IN_PROGRESS,
        FINISHED
    }
}
