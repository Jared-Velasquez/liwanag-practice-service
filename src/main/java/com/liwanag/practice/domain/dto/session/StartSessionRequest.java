package com.liwanag.practice.domain.dto.session;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class StartSessionRequest {
    @NotBlank(message = "Unit ID is required.")
    private String unitId;

    @NotBlank(message = "Episode ID is required.")
    private String episodeId;

    @NotBlank(message = "Activity ID is required.")
    private String activityId;
}
