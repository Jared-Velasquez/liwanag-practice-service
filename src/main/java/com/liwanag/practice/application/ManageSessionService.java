package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.primary.ManageSession;
import com.liwanag.practice.ports.primary.Personalization;
import com.liwanag.practice.ports.primary.QuestionPoolPolicy;
import com.liwanag.practice.ports.secondary.QuestionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class ManageSessionService implements ManageSession {
    private final QuestionStore questionStore;
    private final QuestionPoolPolicy questionPoolPolicy;
    private final Personalization personalizationService;
    @Override
    public Session startSession(UUID userId, FqId fqid) {
        // 1. load canonical questions, generate personalized questions, and create a question pool
        var canonicalQuestions = questionStore.loadQuestions(fqid);
        var personalizedQuestions = personalizationService.generateQuestions(userId, fqid);

        var questionPool = Stream.concat(canonicalQuestions.stream(), personalizedQuestions.stream()).toList();
        questionPoolPolicy.randomShuffle(questionPool);
    }
}
