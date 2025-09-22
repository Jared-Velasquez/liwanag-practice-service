package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public final class ClozeAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @Size(min = 1)
    private List<String> texts; // ensure each element is not blank
    private UUID attemptId;
    private UUID sessionId;

    // Manually create getters to override type()
    public String type() {
        return type;
    }

    public UUID attemptId() {
        return attemptId;
    }

    public UUID sessionId() {
        return sessionId;
    }

    public List<String> getTexts() {
        return texts;
    }
}
