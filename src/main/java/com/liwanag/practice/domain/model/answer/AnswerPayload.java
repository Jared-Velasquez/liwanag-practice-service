package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type",
    visible = true // type is now available to inheriting classes
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = "MCQ"),
        @JsonSubTypes.Type(value = MultipleChoiceMultiAnswer.class, name = "MCQ_MULTI"),
        @JsonSubTypes.Type(value = FillInBlankAnswer.class, name = "FIB"),
        @JsonSubTypes.Type(value = ClozeAnswer.class, name = "CLOZE")
})
public sealed interface AnswerPayload permits MultipleChoiceAnswer, MultipleChoiceMultiAnswer, FillInBlankAnswer, ClozeAnswer  {
    String type();
    UUID attemptId();
    UUID sessionId();
}
