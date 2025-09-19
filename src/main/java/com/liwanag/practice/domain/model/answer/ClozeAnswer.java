package com.liwanag.practice.domain.model.answer;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ClozeAnswer implements AnswerPayload {
    @Size(min = 1)
    private java.util.List<String> texts; // ensure each element is not blank
}
