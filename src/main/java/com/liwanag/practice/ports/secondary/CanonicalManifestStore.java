package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.content.ActivityManifestHandle;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;
import java.util.Optional;

public interface CanonicalManifestStore {
    Optional<List<Question>> load(ActivityManifestHandle handle);
}
