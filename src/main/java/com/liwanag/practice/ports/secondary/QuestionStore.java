package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;

public interface QuestionStore {
    List<Question> loadQuestions(FqId fqid);
}
