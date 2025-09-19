package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public final class MultipleChoiceMultiAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @Size(min = 1)
    private List<String> choiceIds;

    // Manually create getters to override type()
    @Override
    public String type() {
        return type;
    }

    public List<String> getChoiceIds() {
        return choiceIds;
    }
}
