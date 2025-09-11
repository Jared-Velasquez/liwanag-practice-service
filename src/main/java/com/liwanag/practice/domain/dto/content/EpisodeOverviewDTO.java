package com.liwanag.practice.domain.dto.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class EpisodeOverviewDTO {
    private String episodeId;
    private String title;
    private String description;
    private List<ActivityOverviewDTO> activities;
}
