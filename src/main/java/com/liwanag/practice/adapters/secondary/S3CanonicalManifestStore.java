package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.content.ActivityManifestHandle;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.secondary.CanonicalManifestStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class S3CanonicalManifestStore implements CanonicalManifestStore {
    @Override
    public List<Question> load(ActivityManifestHandle handle) {
        return List.of();
    }
}
