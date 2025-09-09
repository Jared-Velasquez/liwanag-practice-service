package com.liwanag.practice.utils;

import java.util.UUID;

public final class SessionKeys {
    private SessionKeys() {}

    public static String sessionPk(UUID userId) {
        return String.format("USER#%s", userId);
    }

    public static String sessionSk(String unitId, String episodeId, String activityId) {
        return String.format("SESSION#%s#%s#%s", unitId, episodeId, activityId);
    }
}
