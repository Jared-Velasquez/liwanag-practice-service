package com.liwanag.practice.domain.model.event;

import com.liwanag.practice.domain.model.answer.AnswerEvaluation;
import com.liwanag.practice.domain.model.answer.Result;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.handler.ServiceInconsistencyException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * @param fqid Must be activity FqId
 */
@Builder
public record AnswerEvaluatedEvent(@NotNull UUID userId, @NotNull String questionId, @NotNull FqId fqid,
                                   @NotNull Result result, @NotNull Instant timestamp) implements Event {
    @Override
    public DetailType detailType() {
        return DetailType.ANSWER_EVALUATED;
    }

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
