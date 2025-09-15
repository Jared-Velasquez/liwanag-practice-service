package com.liwanag.practice.domain.model.session;

import com.liwanag.practice.domain.model.content.FqId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public final class Session {
    private final UUID sessionId;
    private final UUID userId;
    private final FqId activityFqId;
    private final Integer activityVersion;
    private Status status;

    private Integer currentIndex;
    private UUID turnToken;
    private Instant leaseExpiresAt;
    private ManifestHandle manifestHandle;

    private Integer attempted;
    private Integer correct;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;

    private final static Integer LEASE_DURATION_MINUTES = 5;

    public Boolean hasNext(int total) {
        return this.currentIndex < total;
    }

    public Integer nextIndex() {
        return this.currentIndex;
    }

    public void advance() {
        this.currentIndex++;
    }

    public enum Status {
        IDLE,
        IN_PROGRESS,
        FINISHED
    }

    public static Session start(UUID sessionId, UUID userId, FqId activityFqId, ManifestHandle handle) {
        return Session.builder()
                .sessionId(sessionId)
                .userId(userId)
                .activityFqId(activityFqId)
                .activityVersion(null) // TODO: set activity version
                .status(Status.IDLE)
                .currentIndex(0)
                .turnToken(UUID.randomUUID())
                .leaseExpiresAt(Instant.now().plusSeconds(LEASE_DURATION_MINUTES * 60))
                .manifestHandle(handle)
                .attempted(0)
                .correct(0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .completedAt(null)
                .build();
    }
}
