package com.liwanag.practice.models;

import com.liwanag.practice.models.questions.Question;

import java.util.List;

public record ActivityManifest(String unitId, String episodeId, String activityId, String activityFqid, String title, Integer version, String locale, Integer total, List<Question> questions) {
}
