package com.liwanag.practice.domain.dto.questions;

import java.util.List;

public record FillInBlankQuestionDTO(String qid, String type, String difficulty, List<String> tags, String stem) implements QuestionDTO {
}
