package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.domain.model.session.SessionManifestHandle;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import io.awspring.cloud.s3.S3Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class S3SessionManifestStore implements QuestionManifestStore {
    private final S3Template s3Template;
    private final String bucket;

    public S3SessionManifestStore(
            S3Template s3Template,
            @Value("${manifest.session.bucket}") String bucket
    ) {
        this.s3Template = s3Template;
        this.bucket = bucket;
    }

    @Override
    public SessionManifestHandle save(UUID sessionId, List<Question> questions) {
        String manifestKey = key(sessionId);
        ManifestWrapper wrapper = new ManifestWrapper(questions);
        s3Template.store(bucket, manifestKey, wrapper);

        return SessionManifestHandle.fromSessionId(sessionId);
    }

    @Override
    public List<Question> load(UUID sessionId) {
        // Note: using List.class for deserialization is not type-safe (according to IntelliJ);
        // use a wrapper class
        String manifestKey = key(sessionId);
        ManifestWrapper wrapper = s3Template.read(bucket, manifestKey, ManifestWrapper.class);
        return wrapper.questions();
    }

    private String key(UUID sessionId) {
        return String.format("manifests/m_%s.json", sessionId.toString());
    }

    private record ManifestWrapper(List<Question> questions) {
    }
}
