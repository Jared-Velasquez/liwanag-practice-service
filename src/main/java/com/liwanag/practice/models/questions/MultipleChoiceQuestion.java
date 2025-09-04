package com.liwanag.practice.models.questions;

import java.util.List;
import java.util.Map;

public record MultipleChoiceQuestion(String qid, String type, String difficulty, List<String> tags, String stem, List<Choice> choices, Map<String, String> feedback) implements Question {
}
