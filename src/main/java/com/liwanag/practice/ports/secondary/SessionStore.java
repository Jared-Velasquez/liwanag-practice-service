package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionStore {
//    Boolean isSessionActive(UUID userId, FqId fqid);
    void save(Session session);
    Optional<Session> load(UUID sessionId, UUID userId);
}
