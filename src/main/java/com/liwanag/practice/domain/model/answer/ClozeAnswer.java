package com.liwanag.practice.domain.model.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ClozeAnswer implements AnswerPayload {
    @JsonProperty("type")
    private String type;
    @Size(min = 1)
    private List<String> texts; // ensure each element is not blank

    // Manually create getters to override type()
    public String type() {
        return type;
    }

    public List<String> getTexts() {
        return texts;
    }
}
