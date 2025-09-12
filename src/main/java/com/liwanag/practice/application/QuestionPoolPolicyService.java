package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.primary.QuestionPoolPolicy;

import java.util.Collections;
import java.util.List;

public class QuestionPoolPolicyService implements QuestionPoolPolicy {
    @Override
    public void randomShuffle(List<Question> questions) {
        Collections.shuffle(questions);
    }
}
