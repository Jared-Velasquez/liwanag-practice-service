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
    private final FqId fqid;
    private final Integer version;
    private ActivityManifestHandle(String id, FqId fqid, Integer version) {
        if (id == null || !VALID.matcher(id).matches()) {
            throw new IllegalArgumentException("Invalid ActivityManifestHandle format");
        }
        if (fqid == null || !fqid.isActivityFqId()) {
            throw new IllegalArgumentException("FqId must be a valid activity FqId");
        }
        if (version == null || version <= 0) {
            throw new IllegalArgumentException("Version must be a positive integer");
        }
        this.id = id;
        this.fqid = fqid;
        this.version = version;
    }

    public static ActivityManifestHandle of(String id, FqId fqid, Integer version) {
        return new ActivityManifestHandle(id, fqid, version);
    }

    public String getId() {
        return id;
    }
    public FqId getFqId() {
        return fqid;
    }
    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return id;
    }
}
