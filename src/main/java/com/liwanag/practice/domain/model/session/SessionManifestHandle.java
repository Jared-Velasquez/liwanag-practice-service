package com.liwanag.practice.domain.model.session;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Represents an opaque pointer to a session manifest / question pool
 */
public final class SessionManifestHandle implements Serializable {
    private static final Long serialVersionUID = 1L;

    // Accepts m_<uuid> format
    private static final Pattern VALID = Pattern.compile("^m_[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private final String id;

    private SessionManifestHandle(String id) {
        if (id == null || !VALID.matcher("m_" + id).matches()) {
            throw new IllegalArgumentException("Invalid ManifestHandle format");
        }
        this.id = id;
    }

    public static SessionManifestHandle of(String id) {
        return new SessionManifestHandle(id);
    }

    public static SessionManifestHandle fromSessionId(UUID sessionId) {
        return new SessionManifestHandle("m_" + sessionId);
    }

    public String value() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
