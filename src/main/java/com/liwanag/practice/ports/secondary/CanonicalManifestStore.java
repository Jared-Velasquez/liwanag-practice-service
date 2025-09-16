package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.content.ActivityManifestHandle;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;

public interface CanonicalManifestStore {
    List<Question> load(ActivityManifestHandle handle);
}
