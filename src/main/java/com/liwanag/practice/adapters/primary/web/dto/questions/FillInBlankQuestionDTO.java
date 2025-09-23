package com.liwanag.practice.adapters.primary.web.dto.questions;

import lombok.Builder;

import java.util.List;

@Builder
public record FillInBlankQuestionDTO(String qid, String type, String difficulty, List<String> tags, String stem) implements QuestionDTO {
}
