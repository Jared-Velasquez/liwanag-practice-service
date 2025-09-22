package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

public record ClozeAnswer(
        @JsonProperty("type")
        String type,
        @Size(min = 1)
        List<String> texts, // ensure each element is not blank
        @NotNull
        UUID attemptId,
        @NotNull
        UUID sessionId
) implements AnswerPayload {
}