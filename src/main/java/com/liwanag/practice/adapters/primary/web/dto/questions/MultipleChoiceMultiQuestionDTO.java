package com.liwanag.practice.adapters.primary.web.dto.questions;

import java.util.List;
import java.util.Map;

public record MultipleChoiceMultiQuestionDTO(String qid, String type, String difficulty, List<String> tags, String stem, List<ChoiceDTO> choices, Map<String, String> feedback) implements QuestionDTO {
}
