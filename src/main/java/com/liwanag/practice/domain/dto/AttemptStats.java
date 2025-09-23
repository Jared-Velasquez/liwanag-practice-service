package com.liwanag.practice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttemptStats {
    private Integer correct;
    private Integer attempted;
}
