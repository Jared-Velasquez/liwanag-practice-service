package com.liwanag.practice.domain.model.event;

import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.answer.Result;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public final class AnswerEvaluatedEvent implements Event {
    @NotNull
    private final UUID userId;
    @NotNull
    private final String questionId;
    @NotNull
    private final FqId fqid; // Must be activity FqId
    @NotNull
    private final Result result;
    @NotNull
    private final Instant timestamp;

    public static AnswerEvaluatedEvent toEvent(AnswerEvaluation model, UUID userId, FqId fqid) {
        if (!fqid.isActivityFqId()) {
            throw new ServiceInconsistencyException("FqId must be an activity FqId");
        }

        return AnswerEvaluatedEvent.builder()
                .userId(userId)
                .questionId(model.getQuestionId())
                .fqid(fqid)
                .result(model.getResult())
                .timestamp(Instant.now())
                .build();
    }
}
