package com.liwanag.practice.domain.model.content;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Represents an opaque pointer to an activity manifest / canonical question pool
 */
public final class ActivityManifestHandle implements Serializable {
    private static final Long serialVersionUID = 1L;

    // Accepts manifest-<string> format
    private static final Pattern VALID = Pattern.compile("^manifest-[a-zA-Z0-9]+$");

    private final String id;
    private ActivityManifestHandle(String id) {
        if (id == null || !VALID.matcher(id).matches()) {
            throw new IllegalArgumentException("Invalid ActivityManifestHandle format");
        }
        this.id = id;
    }

    public static ActivityManifestHandle of(String id) {
        return new ActivityManifestHandle(id);
    }

    public String value() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
