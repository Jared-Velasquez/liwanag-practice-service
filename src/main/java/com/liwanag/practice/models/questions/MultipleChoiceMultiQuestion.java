package com.liwanag.practice.models.questions;

import java.util.List;
import java.util.Map;

public record MultipleChoiceMultiQuestion(String qid, String type, String difficulty, List<String> tags, String stem, List<Choice> choices, EvalMulti evaluation, Map<String, String> feedback) implements Question {
}
