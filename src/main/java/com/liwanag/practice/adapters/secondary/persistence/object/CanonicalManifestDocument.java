package com.liwanag.practice.adapters.secondary.persistence.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanonicalManifestDocument(
        String unitId,
        String episodeId,
        String activityId,
        String activityFqId,
        String title,
        Integer version,
        String locale,
        Integer total,
        List<Question> questions
) {
}
