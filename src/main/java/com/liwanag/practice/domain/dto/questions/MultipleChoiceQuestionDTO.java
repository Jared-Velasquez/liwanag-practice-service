package com.liwanag.practice.domain.dto.questions;

import java.util.List;

public record MultipleChoiceQuestionDTO(String qid, String type, String difficulty, List<String> tags, String stem, List<ChoiceDTO> choices) implements QuestionDTO {
}
