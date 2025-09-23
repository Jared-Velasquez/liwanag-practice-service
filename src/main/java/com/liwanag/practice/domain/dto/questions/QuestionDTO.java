package com.liwanag.practice.domain.dto.questions;

import java.util.List;

public sealed interface QuestionDTO permits MultipleChoiceQuestionDTO, MultipleChoiceMultiQuestionDTO, FillInBlankQuestionDTO, ClozeQuestionDTO {
    String qid();
    String type();
    String difficulty();
    List<String> tags();
}
