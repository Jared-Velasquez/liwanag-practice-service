package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

public record MultipleChoiceAnswer(
        @JsonProperty("type")
        String type,
        @NotNull
        @NotBlank
        String choiceId,
        @NotNull
        UUID attemptId,
        @NotNull
        UUID sessionId
) implements AnswerPayload {
}