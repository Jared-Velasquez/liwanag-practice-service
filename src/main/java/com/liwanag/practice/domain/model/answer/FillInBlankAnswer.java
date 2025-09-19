package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FillInBlankAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @Size(min = 1, max = 256)
    private String text;

    // Manually create getters to override type()
    @Override
    public String type() {
        return type;
    }

    public String getText() {
        return text;
    }
}
