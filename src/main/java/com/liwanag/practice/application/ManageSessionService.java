package com.liwanag.practice.application;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import com.liwanag.practice.ports.primary.ManageSession;
import com.liwanag.practice.ports.primary.Personalization;
import com.liwanag.practice.ports.primary.QuestionPoolPolicy;
import com.liwanag.practice.ports.secondary.CanonicalManifestStore;
import com.liwanag.practice.ports.secondary.CanonicalStore;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class ManageSessionService implements ManageSession {
    private final CanonicalStore canonicalStore;
    private final CanonicalManifestStore canonicalManifestStore;
    private final QuestionManifestStore questionManifestStore;
    private final QuestionPoolPolicy questionPoolPolicy;
    private final Personalization personalizationService;
    private final SessionStore sessionStore;
    @Override
    public Session startSession(UUID userId, FqId fqid) {
        // TODO: check if there's an active session for this userId and fqid

        // 1. load canonical questions, generate personalized questions, and create a question pool

        log.info("Starting session for userId: {} and activity: {}", userId, fqid);
        log.info("Loading activity: {}", fqid);
        var activity = canonicalStore.loadActivity(fqid).orElseThrow(() -> new NoSuchElementException("Activity not found: " + fqid));
        log.info("Loading canonical questions for activity: {}", fqid);
        var canonicalQuestions = canonicalManifestStore.load(activity.getManifestHandle()).orElseThrow(() -> new ServiceInconsistencyException("Canonical questions not found for activity: " + fqid));
        for (var q : canonicalQuestions) {
            log.info("Canonical question loaded: {}", q.qid());
        }
        var personalizedQuestions = personalizationService.generateQuestions(userId, fqid);

        var questionPool = Stream.concat(canonicalQuestions.stream(), personalizedQuestions.stream()).toList();
        log.info("Shuffling question pool of size: {}", questionPool.size());
        var shuffledQuestions = questionPoolPolicy.randomShuffle(questionPool);

        // 2. create a new session by generating a new sessionId, storing the question pool
        // in the manifest store, and then storing the session in the session store

        var sessionId = UUID.randomUUID();
        log.info("Saving question manifest for sessionId: {}", sessionId);
        var manifestHandle = questionManifestStore.save(sessionId, shuffledQuestions);

        Session session = Session.start(sessionId, userId, fqid, manifestHandle, shuffledQuestions.size());
        log.info("Saving session with id: {}", sessionId);
        sessionStore.save(session);
        return session;
    }
}
