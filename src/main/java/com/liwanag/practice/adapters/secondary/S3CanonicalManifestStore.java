package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.adapters.secondary.persistence.mapper.ActivityManifestMapper;
import com.liwanag.practice.adapters.secondary.persistence.object.CanonicalManifestDocument;
import com.liwanag.practice.domain.model.content.ActivityManifestHandle;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.secondary.CanonicalManifestStore;
import io.awspring.cloud.s3.S3Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class S3CanonicalManifestStore implements CanonicalManifestStore {
    private final ActivityManifestMapper mapper;
    private final S3Template s3Template;
    private final String bucket;

    public S3CanonicalManifestStore(
            ActivityManifestMapper mapper,
            S3Template s3Template,
            @Value("${manifest.activity.bucket}") String bucket
    ) {
        this.mapper = mapper;
        this.s3Template = s3Template;
        this.bucket = bucket;
    }

    @Override
    public List<Question> load(ActivityManifestHandle handle) {
        String manifestKey = mapper.toS3Key(handle);
        CanonicalManifestDocument document = s3Template.read(bucket, manifestKey, CanonicalManifestDocument.class);
        return document.questions();
    }
}
