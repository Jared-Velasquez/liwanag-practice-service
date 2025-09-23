package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.session.SessionManifestHandle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionManifestStore {
    SessionManifestHandle save(UUID sessionId, List<Question> questions);
    Optional<List<Question>> load(UUID sessionId);
}
