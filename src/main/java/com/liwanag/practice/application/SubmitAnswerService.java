package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.answer.Answer;
import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.ports.primary.SubmitAnswer;

import java.util.UUID;

public class SubmitAnswerService implements SubmitAnswer {

    @Override
    public AnswerEvaluation submitAnswer(UUID userId, UUID sessionId, Answer answer) {
        return null;
    }
}
