package com.liwanag.practice.domain.model.questions;

import java.util.List;

public record ClozeBlank(List<String> acceptable, Normalize normalize) {
}
