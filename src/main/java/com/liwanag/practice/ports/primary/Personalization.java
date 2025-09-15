package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;
import java.util.UUID;

public interface Personalization {
    List<Question> generateQuestions(UUID userId, FqId fqid);
}
