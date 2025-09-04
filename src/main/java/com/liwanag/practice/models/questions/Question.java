package com.liwanag.practice.models.questions;

import java.util.List;

public sealed interface Question permits MultipleChoiceQuestion, MultipleChoiceMultiQuestion, FillInBlankQuestion, ClozeQuestion {
    String qid();
    String type();
    String difficulty();
    List<String> tags();
}
