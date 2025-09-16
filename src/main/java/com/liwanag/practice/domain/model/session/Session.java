package com.liwanag.practice.domain.model.session;

import com.liwanag.practice.domain.model.content.FqId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Slf4j
public final class Session {
    private final UUID sessionId;
    private final UUID userId;
    private final FqId activityFqId;
    private final Integer activityVersion;
    private Status status;

    private Integer currentIndex;
    private UUID currentAttemptId; // ID for the current question attempt (not shown to client)
    private UUID turnToken; // Proves that the client is answering the currently open turn and not replaying a stale response
    private Instant leaseExpiresAt;
    private SessionManifestHandle manifestHandle;

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

    public static Session start(UUID sessionId, UUID userId, FqId activityFqId, SessionManifestHandle handle) {
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
                .completedAt(Instant.EPOCH)
                .build();
    }

    // Finite State Machine transitions

    // Move to IN_PROGRESS on first serve
    public void markInProgress() {
        require(status == Status.IDLE, "Session must be in IDLE state to mark as IN_PROGRESS");

        status = Status.IN_PROGRESS;
        updatedAt = Instant.now();
    }

    // Open a new attempt for the current question
    public void openAttempt(UUID attemptId, UUID turnToken, Instant leaseUntil) {
        require(status == Status.IN_PROGRESS || status == Status.IDLE, "Session not active");
        require(this.currentAttemptId == null, "There is already an open attempt");

        this.currentAttemptId = attemptId;
        this.turnToken = turnToken;
        this.leaseExpiresAt = leaseUntil;
    }

    // Verify token and lease before accepting an answer
    public void assertAttemptOpen(UUID attemptId, UUID turnToken, Instant now) {
        require(status == Status.IN_PROGRESS, "Session not active");
        require(this.currentAttemptId != null && this.currentAttemptId.equals(attemptId), "No open attempt for the given attemptId");
        require(this.turnToken.equals(turnToken), "Invalid turn token");
        require(this.leaseExpiresAt == null || this.leaseExpiresAt.isAfter(now), "Lease has expired");
    }

    // Apply the answer, close attempt, and advance to next question
    public void applyAnswer(Boolean isCorrect, Instant now) {
        require(status == Status.IN_PROGRESS, "Session not active");
        require(this.currentAttemptId != null, "No open attempt to apply answer to");

        this.attempted++;
        if (isCorrect != null && isCorrect) {
            this.correct++;
        }
        this.currentAttemptId = null;
        this.turnToken = null;
        this.leaseExpiresAt = null;
        this.currentIndex++;
        this.updatedAt = now;
    }

    // Skip the open attempt; close attempt and advance
    public void skipAttempt(Instant now) {
        require(status == Status.IN_PROGRESS, "Session not active");
        require(this.currentAttemptId != null, "No open attempt to skip");

        this.currentAttemptId = null;
        this.turnToken = null;
        this.leaseExpiresAt = null;
        this.currentIndex++;
        this.updatedAt = now;
    }

    // True if there are more questions given the manifest size
    public Boolean hasNext(Integer totalItems) {
        require(totalItems != null && totalItems >= 0, "Total items must be non-negative");
        return this.currentIndex < totalItems;
    }

    public void completeIfDone(Boolean done, Instant now) {
        if (done && status != Status.FINISHED) {
            require(this.currentAttemptId == null, "Cannot complete session with an open attempt");
            status = Status.FINISHED;
            this.completedAt = now;
        }
    }

    private static void require(Boolean condition, String message) {
        if (!condition) {
            log.info("Precondition failed: {}", message);
            throw new IllegalStateException(message);
        }
    }
}
