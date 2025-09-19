package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public final class MultipleChoiceAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @NotBlank
    private String choiceId;

    // Manually create getters to override type()
    @Override
    public String type() {
        return type;
    }

    public String getChoiceId() {
        return choiceId;
    }
}
