package com.liwanag.practice.domain.model.answer;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FillInBlankAnswer implements AnswerPayload {
    @Size(min = 1, max = 256)
    private String text;
}
