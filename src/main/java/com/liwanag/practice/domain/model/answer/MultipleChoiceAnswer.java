package com.liwanag.practice.domain.model.answer;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MultipleChoiceAnswer implements AnswerPayload {
    @NotBlank
    private String choiceId;
}
