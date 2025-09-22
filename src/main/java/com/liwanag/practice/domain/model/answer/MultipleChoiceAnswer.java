package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public final class MultipleChoiceAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @NotBlank
    private String choiceId;
    private UUID attemptId;
    private UUID sessionId;

    // Manually create getters to override type()
    @Override
    public String type() {
        return type;
    }

    public UUID attemptId() {
        return attemptId;
    }

    public UUID sessionId() {
        return sessionId;
    }

    public String getChoiceId() {
        return choiceId;
    }
}
