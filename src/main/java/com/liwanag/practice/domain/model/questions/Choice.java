package com.liwanag.practice.domain.model.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Choice(String id, String t, @JsonIgnore boolean ok) {
}
