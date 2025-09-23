package com.liwanag.practice.domain.dto.questions;

import com.liwanag.practice.domain.model.questions.ClozeBlank;

import java.util.List;
import java.util.Map;

public record ClozeQuestionDTO(String qid, String type, String difficulty, List<String> tags, String text) implements QuestionDTO {
}
