package com.liwanag.practice.domain.model.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public record FillInBlankQuestion(String qid, String type, String difficulty, List<String> tags, String stem, @JsonIgnore FibEval evaluation, @JsonIgnore Map<String, String> feedback) implements Question {
}
