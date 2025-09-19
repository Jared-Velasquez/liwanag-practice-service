package com.liwanag.practice.domain.model.answer;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MultipleChoiceMultiAnswer implements AnswerPayload {
    @Size(min = 1)
    private List<String> choiceIds;
}
