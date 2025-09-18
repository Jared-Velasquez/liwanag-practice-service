package com.liwanag.practice.domain.dto;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.session.Session;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimNextDTO {
    private ClaimStatus status;
    private String sessionId;

    // Session progress
    private Integer currentQuestionIndex;
    private Integer totalQuestions;
    private AttemptStats attemptStats;

    // Lease
    private Boolean newLease;
    private UUID turnToken;
    private Instant leaseExpiresAt;

    private Question question; // null when status is FINISHED

    public enum ClaimStatus {
        IN_PROGRESS,
        FINISHED
    }

    public static ClaimNextDTO activeLease(Session session, Question question, UUID turnToken, Instant leaseExpiresAt) {
        return new ClaimNextDTO(
                ClaimStatus.IN_PROGRESS,
                session.getSessionId().toString(),
                session.getCurrentIndex() + 1,
                session.getTotalQuestions(),
                AttemptStats.builder()
                        .correct(session.getCorrect())
                        .attempted(session.getCurrentIndex() + 1)
                        .build(),
                true,
                turnToken,
                leaseExpiresAt,
                question
        );
    }

    public static ClaimNextDTO finished(Session session) {
        return new ClaimNextDTO(
                ClaimStatus.FINISHED,
                session.getSessionId().toString(),
                session.getCurrentIndex(),
                session.getTotalQuestions(),
                AttemptStats.builder()
                        .correct(session.getCorrect())
                        .attempted(session.getCurrentIndex())
                        .build(),
                false,
                null,
                null,
                null
        );
    }
}
