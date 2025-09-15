package com.liwanag.practice.domain.dto.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ActivityOverviewDTO {
    private String activityId;
    private String title;
    private String description;
}
