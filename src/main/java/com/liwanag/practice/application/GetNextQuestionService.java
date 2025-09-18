package com.liwanag.practice.application;

import com.liwanag.practice.domain.dto.ClaimNext;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.primary.GetNextQuestion;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class GetNextQuestionService implements GetNextQuestion {
    private final SessionStore sessionStore;

    @Override
    public ClaimNext claimNext(UUID sessionId, UUID userId) {
        Session session = sessionStore.load(sessionId, userId);

        return null;
    }
}
