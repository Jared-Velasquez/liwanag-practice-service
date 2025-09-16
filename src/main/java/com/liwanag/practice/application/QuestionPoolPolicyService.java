package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.primary.QuestionPoolPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionPoolPolicyService implements QuestionPoolPolicy {
    @Override
    public List<Question> randomShuffle(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Question list cannot be null or empty");
        }

        // questions might be immutable, create a mutable copy
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy);
        return copy;
    }
}
