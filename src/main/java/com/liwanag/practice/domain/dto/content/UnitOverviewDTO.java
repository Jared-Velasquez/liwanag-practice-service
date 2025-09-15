package com.liwanag.practice.domain.dto.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UnitOverviewDTO {
    private String unitId;
    private String title;
    private String description;
    private List<EpisodeOverviewDTO> episodes;
}
