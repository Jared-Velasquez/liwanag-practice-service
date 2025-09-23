package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

public record MultipleChoiceMultiAnswer(
        @JsonProperty("type")
        String type,
        @Size(min = 1, max = 4)
        @NotNull
        List<String> choiceIds,
        @NotNull
        UUID attemptId,
        @NotNull
        UUID sessionId
) implements AnswerPayload {
}