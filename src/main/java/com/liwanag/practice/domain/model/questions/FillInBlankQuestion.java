package com.liwanag.practice.domain.model.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public record FillInBlankQuestion(String qid, String type, String difficulty, List<String> tags, String stem, FibEval evaluation, Map<String, String> feedback) implements Question {
}
