package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.answer.AnswerEvaluation;

public interface EventBus {
    void emitAnswerEvaluated(AnswerEvaluation evaluation);
}
