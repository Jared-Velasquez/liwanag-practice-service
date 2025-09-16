package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;

public interface QuestionPoolPolicy {
    /**
     * Randomly shuffle the list of questions in place.
     * @param questions
     */
    List<Question> randomShuffle(List<Question> questions);
}
