package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.domain.model.session.ManifestHandle;

public final class ManifestMapper {
    private final String bucket;

    public ManifestMapper(String bucket) {
        this.bucket = bucket;
    }

    public String toS3Key(ManifestHandle handle) {
        return String.format("manifests/%s.json", handle.value());
    }

    public String toS3URL(ManifestHandle handle) {
        return String.format("s3://%s/%s", bucket, toS3Key(handle));
    }

    public ManifestHandle fromS3KeyOrURL(String s) {
        String file = s.contains("/") ? s.substring(s.lastIndexOf('/') + 1) : s;
        String id = file.endsWith(".json") ? file.substring(0, file.length() - 5) : file;
        return ManifestHandle.of(id);
    }
}
