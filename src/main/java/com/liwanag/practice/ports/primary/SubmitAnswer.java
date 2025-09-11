package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.answer.Answer;
import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.content.FqId;

import java.util.UUID;

public interface SubmitAnswer {
    AnswerEvaluation submitAnswer(UUID userId, UUID sessionId, Answer answer);
}
