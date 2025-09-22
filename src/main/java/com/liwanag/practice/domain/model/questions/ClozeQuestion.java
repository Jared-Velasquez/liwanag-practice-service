package com.liwanag.practice.domain.model.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public record ClozeQuestion(String qid, String type, String difficulty, List<String> tags, String text, @JsonIgnore List<ClozeBlank> blanks, Map<String, String> feedback) implements Question {
}
