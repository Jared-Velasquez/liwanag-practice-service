package com.liwanag.practice.domain.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * Represents a fully-qualified ID for units, episodes, and activities.
 * Example:
 * ACTIVITY#u_1#e_1#a_1
 * EPISODE#u_1#e_1
 * UNIT#u_1
 */
@AllArgsConstructor
@Getter
@Setter
public class FqId {
    private String unitId;
    private String episodeId;
    private String activityId;

    public FqId(String strFqId) {
        // Convert a string such as "ACTIVITY#u_1#e_1#a_1" to FqId object
        String[] parts = strFqId.split("#");
        if (parts.length < 2 || parts.length > 4) {
            throw new IllegalArgumentException("Invalid FqId format");
        }

        switch (parts[0]) {
            case "UNIT" -> {
                if (parts.length != 2) throw new IllegalArgumentException("Invalid UNIT FqId format");
                this.unitId = parts[1];
            }
            case "EPISODE" -> {
                if (parts.length != 3) throw new IllegalArgumentException("Invalid EPISODE FqId format");
                this.unitId = parts[1];
                this.episodeId = parts[2];
            }
            case "ACTIVITY" -> {
                if (parts.length != 4) throw new IllegalArgumentException("Invalid ACTIVITY FqId format");
                this.unitId = parts[1];
                this.episodeId = parts[2];
                this.activityId = parts[3];
            }
            default -> throw new IllegalArgumentException("Invalid FqId prefix");
        }
    }

    private final Pattern UNIT_ID_PATTERN = Pattern.compile("u_[1-9]\\d*$");
    private final Pattern EPISODE_ID_PATTERN = Pattern.compile("e_[1-9]\\d*$");
    private final Pattern ACTIVITY_ID_PATTERN = Pattern.compile("a_[1-9]\\d*$");

    public Boolean isUnitFqId() {
        return unitId != null && UNIT_ID_PATTERN.matcher(unitId).matches();
    }

    public Boolean isEpisodeFqId() {
        return isUnitFqId() && episodeId != null && EPISODE_ID_PATTERN.matcher(episodeId).matches();
    }

    public Boolean isActivityFqId() {
        return isEpisodeFqId() && activityId != null && ACTIVITY_ID_PATTERN.matcher(activityId).matches();
    }

    public String toString() {
        if (isActivityFqId()) {
            return String.format("ACTIVITY#%s#%s#%s", unitId, episodeId, activityId);
        } else if (isEpisodeFqId()) {
            return String.format("EPISODE#%s#%s", unitId, episodeId);
        } else if (isUnitFqId()) {
            return String.format("UNIT#%s", unitId);
        } else {
            throw new IllegalStateException("Invalid FqId");
        }
    }
}
