package com.liwanag.practice.application;

import com.liwanag.practice.domain.dto.ClaimNextDTO;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import com.liwanag.practice.ports.primary.GetNextQuestion;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class GetNextQuestionService implements GetNextQuestion {
    private final SessionStore sessionStore;
    private final QuestionManifestStore questionManifestStore;
    private final Integer LEASE_DURATION_SECONDS = 30;

    @Override
    public ClaimNextDTO claimNext(UUID sessionId, UUID userId) {
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
                sessionStore.save(session);
            }

            return ClaimNextDTO.finished(session);
        }

        // case 2: session is active and there's an active lease
        List<Question> questions = questionManifestStore.load(sessionId).orElseThrow(() -> new ServiceInconsistencyException("Questions not found for session: " + sessionId));
        Question currentQuestion = questions.get(session.getCurrentIndex());

        Instant now = Instant.now();
        if (session.getTurnToken() != null && session.getLeaseExpiresAt() != null && session.getLeaseExpiresAt().isAfter(now)) {
            return ClaimNextDTO.activeLease(
                    session,
                    currentQuestion,
                    session.getTurnToken(),
                    session.getLeaseExpiresAt()
            );
        }

        // case 3: session is active and there's no active lease

        UUID newAttemptId = UUID.randomUUID();
        UUID newTurnToken = UUID.randomUUID();
        Instant newLeaseExpiry = now.plusSeconds(LEASE_DURATION_SECONDS);

        session.openAttempt(newAttemptId, newTurnToken, newLeaseExpiry);
        sessionStore.save(session);

        return ClaimNextDTO.activeLease(
                session,
                currentQuestion,
                newTurnToken,
                newLeaseExpiry
        );
    }
}
