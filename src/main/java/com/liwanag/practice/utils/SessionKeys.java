package com.liwanag.practice.utils;

import com.liwanag.practice.domain.model.content.FqId;

import java.util.UUID;

public final class SessionKeys {
    private SessionKeys() {}

    public static String sessionPk(UUID userId) {
        return String.format("USER#%s", userId);
    }

    public static String sessionSk(UUID sessionId) {
        return String.format("SESSION#%s", sessionId);
    }

    public static UUID extractUserId(String pk) {
        if (!pk.startsWith("USER#")) {
            throw new IllegalArgumentException("Invalid PK format");
        }

        return UUID.fromString(pk.split("#")[1]);
    }

    public static UUID extractSessionId(String sessionId) {
        if (!sessionId.startsWith("SESSION#")) {
            throw new IllegalArgumentException("Invalid SK format");
        }

        return UUID.fromString(sessionId.split("#")[1]);
    }
}
