package com.liwanag.practice.domain.model.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY, // use existing field "type" from JSON
        property = "type",
        visible = true                               // keep "type" available to subtypes/getters
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class,       name = "MCQ"),
        @JsonSubTypes.Type(value = MultipleChoiceMultiQuestion.class,  name = "MCQ_MULTI"),
        @JsonSubTypes.Type(value = FillInBlankQuestion.class,       name = "FIB"),
        @JsonSubTypes.Type(value = ClozeQuestion.class,     name = "CLOZE")
})
public sealed interface Question permits MultipleChoiceQuestion, MultipleChoiceMultiQuestion, FillInBlankQuestion, ClozeQuestion {
    String qid();
    String type();
    String difficulty();
    List<String> tags();
}
