package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.primary.ManageSession;
import com.liwanag.practice.ports.primary.Personalization;
import com.liwanag.practice.ports.primary.QuestionPoolPolicy;
import com.liwanag.practice.ports.secondary.CanonicalStore;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class ManageSessionService implements ManageSession {
    private final CanonicalStore canonicalStore;
    private final QuestionManifestStore questionManifestStore;
    private final QuestionPoolPolicy questionPoolPolicy;
    private final Personalization personalizationService;
    private final SessionStore sessionStore;
    @Override
    public Session startSession(UUID userId, FqId fqid) {
        // 1. load canonical questions, generate personalized questions, and create a question pool

        var canonicalQuestions = canonicalStore.loadQuestions(fqid);
        var personalizedQuestions = personalizationService.generateQuestions(userId, fqid);

        var questionPool = Stream.concat(canonicalQuestions.stream(), personalizedQuestions.stream()).toList();
        questionPoolPolicy.randomShuffle(questionPool);

        // 2. create a new session by generating a new sessionId, storing the question pool
        // in the manifest store, and then storing the session in the session store

        var sessionId = UUID.randomUUID();
        var manifestHandle = questionManifestStore.save(sessionId, questionPool);

        Session session = Session.start(sessionId, userId, fqid, manifestHandle);
        sessionStore.save(session);
        return session;
    }
}
