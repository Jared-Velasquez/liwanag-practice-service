package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.session.ManifestHandle;

import java.util.List;
import java.util.UUID;

public interface QuestionManifestStore {
    ManifestHandle save(UUID sessionId, List<Question> questions);
    List<Question> load(UUID sessionId);
}
