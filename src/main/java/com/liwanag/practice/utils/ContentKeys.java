package com.liwanag.practice.utils;

import org.springframework.lang.Contract;

public final class ContentKeys {
    private ContentKeys() {}

    public static String unitPk(String unitId) {
        return String.format("UNIT#%s", unitId);
    }

    public static String episodePk(String unitId, String episodeId) {
        return String.format("EPISODE#%s#%s", unitId, episodeId);
    }

    public static String activityPk(String unitId, String episodeId, String activityId) {
        return String.format("ACTIVITY#%s#%s#%s", unitId, episodeId, activityId);
    }

    public static String liveSk() {
        return "LIVE";
    }
}
