package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.answer.AnswerPayload;

import java.util.UUID;

public interface SubmitAnswer {
    AnswerEvaluation submitAnswer(UUID userId, UUID sessionId, AnswerPayload answer);
}
