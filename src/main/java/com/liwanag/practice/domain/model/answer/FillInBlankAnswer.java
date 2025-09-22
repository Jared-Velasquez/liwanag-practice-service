package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public final class FillInBlankAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @Size(min = 1, max = 256)
    private String text;
    private UUID attemptId;
    private UUID sessionId;

    public UUID attemptId() {
        return attemptId;
    }

    public UUID sessionId() {
        return sessionId;
    }

    // Manually create getters to override type()
    @Override
    public String type() {
        return type;
    }

    public String getText() {
        return text;
    }
}
