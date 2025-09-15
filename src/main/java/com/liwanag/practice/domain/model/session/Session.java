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
    private Long leaseExpiresAt;
    private ManifestHandle manifestHandle;

    private Integer attempted;
    private Integer correct;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;

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
}
