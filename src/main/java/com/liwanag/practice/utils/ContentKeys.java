package com.liwanag.practice.utils;

import com.liwanag.practice.domain.model.content.FqId;

public final class ContentKeys {
    private ContentKeys() {}

    public static String unitPk(FqId fqid) {
        if (!fqid.isUnitFqId())
            throw new IllegalArgumentException("FqId is not a unit FqId");
        return String.format("UNIT#%s", fqid.getUnitId());
    }

    public static String episodePk(FqId fqid) {
        if (!fqid.isEpisodeFqId())
            throw new IllegalArgumentException("FqId is not an episode FqId");
        return String.format("EPISODE#%s#%s", fqid.getUnitId(), fqid.getEpisodeId());
    }

    public static String activityPk(FqId fqid) {
        if (!fqid.isActivityFqId())
            throw new IllegalArgumentException("FqId is not an activity FqId");
        return String.format("ACTIVITY#%s#%s#%s", fqid.getUnitId(), fqid.getEpisodeId(), fqid.getActivityId());
    }

    public static String liveSk() {
        return "LIVE";
    }
}
