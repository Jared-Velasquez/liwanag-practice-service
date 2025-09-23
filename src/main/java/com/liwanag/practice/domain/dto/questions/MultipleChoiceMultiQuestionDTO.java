package com.liwanag.practice.domain.dto.questions;

import com.liwanag.practice.domain.model.questions.Choice;
import com.liwanag.practice.domain.model.questions.EvalMulti;

import java.util.List;
import java.util.Map;

public record MultipleChoiceMultiQuestionDTO(String qid, String type, String difficulty, List<String> tags, String stem, List<ChoiceDTO> choices, Map<String, String> feedback) implements QuestionDTO {
}
