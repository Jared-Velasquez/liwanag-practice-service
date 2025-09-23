package com.liwanag.practice.adapters.primary.web.dto.questions;

import lombok.Builder;

import java.util.List;

@Builder
public record ClozeQuestionDTO(String qid, String type, String difficulty, List<String> tags, String text) implements QuestionDTO {
}
