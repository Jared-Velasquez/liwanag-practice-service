package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.primary.Personalization;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonalizationService implements Personalization {
    public List<Question> generateQuestions(UUID userId, FqId fqid) {
        return new ArrayList<>();
    }
}
