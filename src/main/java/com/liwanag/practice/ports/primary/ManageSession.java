package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;

import java.util.UUID;

public interface ManageSession {
    Session startSession(UUID userId, FqId fqid);
    void endSession(UUID userId, UUID sessionId);
}
