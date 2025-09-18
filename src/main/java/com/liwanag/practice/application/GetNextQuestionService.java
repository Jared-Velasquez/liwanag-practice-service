package com.liwanag.practice.application;

import com.liwanag.practice.domain.dto.ClaimNext;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.primary.GetNextQuestion;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class GetNextQuestionService implements GetNextQuestion {
    private final SessionStore sessionStore;
    private final Integer LEASE_DURATION_SECONDS = 30;

    @Override
    public ClaimNext claimNext(UUID sessionId, UUID userId) {
        // Three cases when calling claimNext:
        // 1. session is inactive since all questions have been answered/skipped
        // 2. session is active and the user has already been served a question (lease is active)
        // 3. session is active and the user has not been served a question since starting / answering
        // previous question (no active lease)

        Session session = sessionStore.load(sessionId, userId).orElseThrow(() -> new NoSuchElementException("Session not found"));

        // case 1: session is inactive
        if (session.getCurrentIndex() >= session.getTotalQuestions()) {
            if (session.getStatus() != Session.Status.FINISHED) {
                session.completeIfDone(true, Instant.now());

                // TODO: then save session
            }

            // TODO: return ClaimNext here
        }

        // case 2: session is active and there's an active lease
        Instant now = Instant.now();
        if (session.getTurnToken() != null && session.getLeaseExpiresAt() != null && session.getLeaseExpiresAt().isAfter(now)) {
            // TODO: serve the same question again

            // TODO: then save session

            // TODO: return ClaimNext here
        }

        UUID newAttemptId = UUID.randomUUID();
        UUID newTurnToken = UUID.randomUUID();
        Instant newLeaseExpiry = now.plusSeconds(LEASE_DURATION_SECONDS);

        session.openAttempt(newAttemptId, newTurnToken, newLeaseExpiry);

        // TODO: serve the next question

        // TODO: then save session

        // TODO: return ClaimNext here

        return null;
    }
}
