package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.answer.AnswerPayload;
import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.questions.QuestionType;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import com.liwanag.practice.ports.primary.SubmitAnswer;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class SubmitAnswerService implements SubmitAnswer {
    private final SessionStore sessionStore;
    private final QuestionManifestStore questionManifestStore;

    @Override
    public AnswerEvaluation submit(UUID userId, UUID sessionId, AnswerPayload answer) {
        // Three cases when calling submitAnswer:
        // 1. The session has finished
        // 2. The session has not yet served a question to the user (no active lease)
        // 3. The session has an active lease

        Session session = sessionStore.load(sessionId, userId).orElseThrow(() -> new NoSuchElementException("Session not found"));

        // case 1: session has finished
        if (session.getStatus() == Session.Status.FINISHED) {
            throw new IllegalStateException("Session has already finished");
        }

        // case 2: session has no active lease
        if (session.getTurnToken() == null || session.getLeaseExpiresAt() == null || session.getLeaseExpiresAt().isBefore(java.time.Instant.now())) {
            throw new IllegalStateException("No active lease for session");
        }

        // case 3: session has an active lease
        // TODO: Evaluate the answer, update the session state, and save the session
        Question currentQuestion = questionManifestStore.load(sessionId)
                .orElseThrow(() -> new ServiceInconsistencyException("Questions not found for session: " + sessionId))
                .get(session.getCurrentIndex());
        AnswerEvaluation evaluation = evaluate(currentQuestion, answer);
        // TODO: emit AnswerEvaluated event

        return null;
    }

    @Override
    public AnswerEvaluation evaluate(Question question, AnswerPayload answer) {
        // Evaluate the answer against the current question in the session

        // Check question type
        if (!question.type().equals(answer.type())) {
            throw new IllegalArgumentException("Answer type does not match question type");
        }

        QuestionType questionType;
        // Convert type to QuestionType enum
        try {
            questionType = QuestionType.valueOf(question.type());
        } catch (IllegalArgumentException e) {
            // Question type not in enum even though it passed validation
            throw new ServiceInconsistencyException("Unknown question type: " + question.type());
        }

        AnswerEvaluation result;
        switch (questionType) {
            case MCQ -> {
                result = evaluateMCQ(question, answer);
            }
            case MCQ_MULTI -> {
                result = evaluateMCQM(question, answer);
            }
            case FIB -> {
                result = evaluateFIB(question, answer);
            }
            case CLOZE -> {
                result = evaluateCloze(question, answer);
            }
            default -> {
                // Even though all enum values are covered, IntelliJ requires a default case
                throw new ServiceInconsistencyException("Unhandled question type: " + questionType);
            }
        }

        // TODO: update session
        // TODO: emit event

        return result;
    }

    private AnswerEvaluation evaluateMCQ(Question question, AnswerPayload answer) {
        // TODO: Implement MCQ evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateMCQM(Question question, AnswerPayload answer) {
        // TODO: Implement MCQ_MULTI evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateFIB(Question question, AnswerPayload answer) {
        // TODO: Implement FIB evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateCloze(Question question, AnswerPayload answer) {
        // TODO: Implement CLOZE evaluation logic
        return null;
    }
}
