package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

public record FillInBlankAnswer(
        @JsonProperty("type")
        String type,
        @Size(min = 1, max = 256)
        @NotNull
        String text, // ensure not blank
        @NotNull
        UUID attemptId,
        @NotNull
        UUID sessionId
) implements AnswerPayload {
}
