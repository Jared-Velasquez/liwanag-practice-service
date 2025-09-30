package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.answer.*;
import com.liwanag.practice.domain.model.event.AnswerEvaluatedEvent;
import com.liwanag.practice.domain.model.event.SessionFinishedEvent;
import com.liwanag.practice.domain.model.questions.*;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import com.liwanag.practice.ports.primary.SubmitAnswer;
import com.liwanag.practice.ports.secondary.EventBus;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import com.liwanag.practice.utils.SessionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class SubmitAnswerService implements SubmitAnswer {
    private final SessionStore sessionStore;
    private final QuestionManifestStore questionManifestStore;
    private final EventBus eventBus;

    @Override
    public AnswerEvaluation submit(UUID userId, UUID sessionId, AnswerPayload answer) {
        // Three cases when calling submitAnswer:
        // 1. The session has finished
        // 2. The session has not yet served a question to the user (no active lease)
        // 3. The session has an active lease

        if (!sessionId.equals(answer.sessionId())) {
            // print out sessionId and answer's sessionId
            log.info("Session ID mismatch: sessionId={}, answer.sessionId={}", sessionId, answer.sessionId());
            throw new IllegalArgumentException("Session ID in URL does not match session ID in answer payload");
        }

        Session session = sessionStore.load(sessionId, userId).orElseThrow(() -> new NoSuchElementException("Session not found"));

        // case 1: session has finished
        if (session.getStatus() == Session.Status.FINISHED) {
            throw new IllegalArgumentException("Session has already finished");
        }

        // case 2: session has no active lease
        if (session.getTurnToken() == null || session.getLeaseExpiresAt() == null || session.getLeaseExpiresAt().isBefore(java.time.Instant.now())) {
            throw new IllegalArgumentException("No active lease for session");
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

        // After applying answer, refresh the session object with a new attempt
        if (!evaluation.getShouldAdvance() && session.getStatus() != Session.Status.FINISHED) {
            // User answered incorrectly, do not advance to next question
            log.info("User answered incorrectly, not advancing to next question");
            UUID newAttemptId = UUID.randomUUID();
            UUID newTurnToken = UUID.randomUUID();
            Instant newLeaseExpiry = Instant.now().plusSeconds(SessionConstants.LEASE_DURATION_SECONDS);

            session.openAttempt(newAttemptId, newTurnToken, newLeaseExpiry);
        }

        // TODO: ensure that attempts increment properly for incorrect answers
        sessionStore.save(session);

        log.info("Emitting AnswerEvaluatedEvent for session: {}", sessionId);
        AnswerEvaluatedEvent event = AnswerEvaluatedEvent.toEvent(evaluation, userId, session.getActivityFqId());
        eventBus.emit(event);

        log.info("Session status after applying answer: {}", session.getStatus());

        if (session.getStatus() == Session.Status.FINISHED) {
            log.info("Emitting SessionFinishedEvent for session: {}", sessionId);
            SessionFinishedEvent sessionFinishedEvent = SessionFinishedEvent.builder()
                    .userId(userId)
                    .sessionId(sessionId)
                    .fqid(session.getActivityFqId())
                    .timestamp(Instant.now())
                    .build();
            eventBus.emit(sessionFinishedEvent);
        }

        return evaluation;
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

        // Get the correct question ID from the choices list
        List<Choice> choices = question.choices();
        String correctQid = choices.stream()
                .filter(Choice::ok)
                .findFirst()
                .map(Choice::id)
                .orElseThrow(() -> new ServiceInconsistencyException("No correct choice found for question: " + question.qid()));

        if (answer.choiceId().equals(correctQid)) {
            // Correct answer
            return AnswerEvaluation.builder()
                    .attemptId(answer.attemptId())
                    .sessionId(answer.sessionId())
                    .questionId(question.qid())
                    .result(Result.CORRECT)
                    .experienceEarned(10)
                    .shouldAdvance(true)
                    .submittedAt(Instant.now())
                    .evaluatedAt(Instant.now())
                    .build();
        } else {
            // Incorrect answer
            return AnswerEvaluation.builder()
                    .attemptId(answer.attemptId())
                    .sessionId(answer.sessionId())
                    .questionId(question.qid())
                    .result(Result.INCORRECT)
                    .experienceEarned(0) // No experience points for incorrect answer
                    .shouldAdvance(false) // Do not advance on incorrect answer
                    .submittedAt(Instant.now())
                    .evaluatedAt(Instant.now())
                    .build();
        }
    }

    private AnswerEvaluation evaluateMCMQ(MultipleChoiceMultiQuestion question, MultipleChoiceMultiAnswer answer) {
        // An answer is correct if all selected choices are correct and no incorrect choices are selected
        List<String> correctChoiceIds = question.choices().stream()
                .filter(Choice::ok)
                .map(Choice::id)
                .toList();
        List<String> selectedChoiceIds = answer.choiceIds();

        // Ensure no duplicates were selected in the answer
        if (selectedChoiceIds.size() != new HashSet<>(selectedChoiceIds).size()) {
            throw new IllegalArgumentException("Duplicate choices selected in answer");
        }

        // Direct "containsAll" with lists can have poor performance; convert to sets
        if (new HashSet<>(selectedChoiceIds).containsAll(correctChoiceIds) && new HashSet<>(correctChoiceIds).containsAll(selectedChoiceIds)) {
            // Correct answer
            return AnswerEvaluation.builder()
                    .attemptId(answer.attemptId())
                    .sessionId(answer.sessionId())
                    .questionId(question.qid())
                    .result(Result.CORRECT)
                    .experienceEarned(10)
                    .shouldAdvance(true)
                    .submittedAt(Instant.now())
                    .evaluatedAt(Instant.now())
                    .build();
        } else {
            // Incorrect answer
            return AnswerEvaluation.builder()
                    .attemptId(answer.attemptId())
                    .sessionId(answer.sessionId())
                    .questionId(question.qid())
                    .result(Result.INCORRECT)
                    .experienceEarned(0) // No experience points for incorrect answer
                    .shouldAdvance(false) // Do not advance on incorrect answer
                    .submittedAt(Instant.now())
                    .evaluatedAt(Instant.now())
                    .build();
        }
    }

    private AnswerEvaluation evaluateFIBQ(FillInBlankQuestion question, FillInBlankAnswer answer) {
        List<String> acceptableAnswers = question.evaluation().acceptableAnswers();

        for (String acc : acceptableAnswers) {
            int distance = levenshteinDistance(acc.toLowerCase(), answer.text().toLowerCase());
            if (distance <= question.evaluation().levenshtein().maxDistance()) {
                // Correct answer
                return AnswerEvaluation.builder()
                        .attemptId(answer.attemptId())
                        .sessionId(answer.sessionId())
                        .questionId(question.qid())
                        .result(Result.CORRECT)
                        .experienceEarned(10)
                        .shouldAdvance(true)
                        .submittedAt(Instant.now())
                        .evaluatedAt(Instant.now())
                        .build();
            }
        }

        // Incorrect answer
        return AnswerEvaluation.builder()
                .attemptId(answer.attemptId())
                .sessionId(answer.sessionId())
                .questionId(question.qid())
                .result(Result.INCORRECT)
                .experienceEarned(0) // No experience points for incorrect answer
                .shouldAdvance(false) // Do not advance on incorrect answer
                .submittedAt(Instant.now())
                .evaluatedAt(Instant.now())
                .build();
    }

    private Integer levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j; // Deletion
                } else if (j == 0) {
                    dp[i][j] = i; // Insertion
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], // Deletion
                                    Math.min(dp[i][j - 1], // Insertion
                                            dp[i - 1][j - 1])); // Substitution
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private AnswerEvaluation evaluateClozeQ(ClozeQuestion question, ClozeAnswer answer) {
        // TODO: Implement CLOZE evaluation logic
        List<ClozeBlank> blanks = question.blanks();
        List<String> answers = answer.texts();

        // Answers must match number and order of blanks
        if (blanks.size() != answers.size()) {
            throw new IllegalArgumentException("Number of answers does not match number of blanks");
        }

        for (int i = 0; i < blanks.size(); i++) {
            ClozeBlank blank = blanks.get(i);
            String userAnswer = answers.get(i);
            List<String> acceptableAnswers = blank.acceptable();

            boolean matchFound = false;
            for (String acc : acceptableAnswers) {
                int distance = levenshteinDistance(acc.toLowerCase(), userAnswer.toLowerCase());
                if (distance <= blank.normalize().levenshtein().maxDistance()) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                // Incorrect answer
                return AnswerEvaluation.builder()
                        .attemptId(answer.attemptId())
                        .sessionId(answer.sessionId())
                        .questionId(question.qid())
                        .result(Result.INCORRECT)
                        .experienceEarned(0) // No experience points for incorrect answer
                        .shouldAdvance(false) // Do not advance on incorrect answer
                        .submittedAt(Instant.now())
                        .evaluatedAt(Instant.now())
                        .build();
            }
        }

        return AnswerEvaluation.builder()
                .attemptId(answer.attemptId())
                .sessionId(answer.sessionId())
                .questionId(question.qid())
                .result(Result.CORRECT)
                .experienceEarned(10)
                .shouldAdvance(true)
                .submittedAt(Instant.now())
                .evaluatedAt(Instant.now())
                .build();
    }
}
