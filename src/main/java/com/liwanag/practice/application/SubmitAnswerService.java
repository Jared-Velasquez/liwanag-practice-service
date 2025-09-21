package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.answer.*;
import com.liwanag.practice.domain.model.questions.*;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import com.liwanag.practice.ports.primary.SubmitAnswer;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
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
        // evaluate the answer
        log.info("Evaluating answer for session: {}", sessionId);
        log.info("Current question index: {}", session.getCurrentIndex());
        Question currentQuestion = questionManifestStore.load(sessionId)
                .orElseThrow(() -> new ServiceInconsistencyException("Questions not found for session: " + sessionId))
                .get(session.getCurrentIndex());
        log.info("Evaluating answer for question: {}", currentQuestion.qid());
        AnswerEvaluation evaluation = evaluate(currentQuestion, answer);

        // update session
        log.info("Updating session: {}", sessionId);
        session.applyAnswer(evaluation.getShouldAdvance(), Instant.now());
        sessionStore.save(session);

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

        return switch (answer) {
            case MultipleChoiceAnswer ans when question instanceof MultipleChoiceQuestion mcq -> evaluateMCQ(mcq, ans);
            case MultipleChoiceMultiAnswer ans when question instanceof MultipleChoiceMultiQuestion mcmq -> evaluateMCMQ(mcmq, ans);
            case FillInBlankAnswer ans when question instanceof FillInBlankQuestion fibq -> evaluateFIBQ(fibq, ans);
            case ClozeAnswer ans when question instanceof ClozeQuestion clozeQ -> evaluateClozeQ(clozeQ, ans);

            // Mismatches
            case MultipleChoiceAnswer ans -> throw new IllegalArgumentException("Answer type does not match question type");
            case MultipleChoiceMultiAnswer ans -> throw new IllegalArgumentException("Answer type does not match question type");
            case FillInBlankAnswer ans -> throw new IllegalArgumentException("Answer type does not match question type");
            case ClozeAnswer ans -> throw new IllegalArgumentException("Answer type does not match question type");
        };
    }

    private AnswerEvaluation evaluateMCQ(MultipleChoiceQuestion question, MultipleChoiceAnswer answer) {
        // TODO: Implement MCQ evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateMCMQ(MultipleChoiceMultiQuestion question, MultipleChoiceMultiAnswer answer) {
        // TODO: Implement MCQ_MULTI evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateFIBQ(FillInBlankQuestion question, FillInBlankAnswer answer) {
        // TODO: Implement FIB evaluation logic
        return null;
    }

    private AnswerEvaluation evaluateClozeQ(ClozeQuestion question, ClozeAnswer answer) {
        // TODO: Implement CLOZE evaluation logic
        return null;
    }
}
