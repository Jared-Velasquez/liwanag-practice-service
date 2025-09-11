package com.liwanag.practice.domain.model.questions;

import java.util.List;
import java.util.Map;

public record ClozeQuestion(String qid, String type, String difficulty, List<String> tags, String text, List<ClozeBlank> blanks, Map<String, String> feedback) implements Question {
}
