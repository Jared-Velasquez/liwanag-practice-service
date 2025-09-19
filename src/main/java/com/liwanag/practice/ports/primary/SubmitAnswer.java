package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.answer.AnswerPayload;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.UUID;

public interface SubmitAnswer {
    AnswerEvaluation submit(UUID userId, UUID sessionId, AnswerPayload answer);
    AnswerEvaluation evaluate(Question question, AnswerPayload answer);
}
