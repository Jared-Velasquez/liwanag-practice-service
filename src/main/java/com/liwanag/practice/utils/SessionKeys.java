package com.liwanag.practice.utils;

import com.liwanag.practice.domain.model.content.FqId;

import java.util.UUID;

public final class SessionKeys {
    private SessionKeys() {}

    public static String sessionPk(UUID userId) {
        return String.format("USER#%s", userId);
    }

    public static String sessionSk(FqId fqid) {
        if (!fqid.isActivityFqId())
            throw new IllegalArgumentException("FqId is not an activity FqId");
        return String.format("SESSION#%s#%s#%s", fqid.getUnitId(), fqid.getEpisodeId(), fqid.getActivityId());
    }
}
