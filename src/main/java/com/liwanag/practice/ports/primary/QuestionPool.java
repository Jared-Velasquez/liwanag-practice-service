package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionPool {
    void generatePool(UUID sessionId);
    List<Question> getPool(UUID sessionId);
}
