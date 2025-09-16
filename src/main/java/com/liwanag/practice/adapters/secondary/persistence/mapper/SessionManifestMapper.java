package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.domain.model.session.SessionManifestHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class SessionManifestMapper {
    private final String bucket;

    public SessionManifestMapper(@Value("${manifest.session.bucket}") String bucket) {
        this.bucket = bucket;
    }

    public String toS3Key(SessionManifestHandle handle) {
        return String.format("manifests/%s.json", handle.value());
    }

    public String toS3URL(SessionManifestHandle handle) {
        return String.format("s3://%s/%s", bucket, toS3Key(handle));
    }

    public SessionManifestHandle fromS3KeyOrURL(String s) {
        String file = s.contains("/") ? s.substring(s.lastIndexOf('/') + 1) : s;
        String id = file.endsWith(".json") ? file.substring(0, file.length() - 5) : file;
        return SessionManifestHandle.of(id);
    }
}
