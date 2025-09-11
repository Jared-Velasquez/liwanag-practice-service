package com.liwanag.practice.domain.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
@Setter
public class FqId {
    private String unitId;
    private String episodeId;
    private String activityId;

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
}
