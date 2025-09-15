package com.liwanag.practice.domain.model.session;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Represents an opaque pointer to a session manifest / question pool
 */
public final class ManifestHandle implements Serializable {
    private static final Long serialVersionUID = 1L;

    // Accepts m_<uuid> format
    private static final Pattern VALID = Pattern.compile("^m_[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private final String id;

    private ManifestHandle(String id) {
        if (id == null || !VALID.matcher("m_" + id).matches()) {
            throw new IllegalArgumentException("Invalid ManifestHandle format");
        }
        this.id = id;
    }

    public static ManifestHandle of(String id) {
        return new ManifestHandle(id);
    }

    public static ManifestHandle fromSessionId(UUID sessionId) {
        return new ManifestHandle("m_" + sessionId);
    }

    public String value() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
