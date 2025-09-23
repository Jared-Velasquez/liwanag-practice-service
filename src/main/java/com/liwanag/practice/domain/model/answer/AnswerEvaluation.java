package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON serialization
public class AnswerEvaluation {
    @NotNull
    private UUID attemptId;
    @NotNull
    private UUID sessionId;
    @NotBlank
    private String questionId;

    @NotNull
    private Result result;
    @NotNull
    private Integer experienceEarned;
    @Builder.Default
    @JsonIgnore
    private Boolean shouldAdvance = false;

    @NotNull
    @JsonIgnore
    private Instant submittedAt;
    @NotNull
    @JsonIgnore
    private Instant evaluatedAt;
}
