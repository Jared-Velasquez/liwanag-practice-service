package com.liwanag.practice.models.questions;

import java.util.List;

public record FibEval(String mode, List<String> acceptableAnswers, Levenshtein levenshtein, Normalize normalize, List<String> patterns) {
}
