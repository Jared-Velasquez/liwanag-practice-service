package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.domain.model.content.ActivityManifestHandle;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.SessionManifestHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class ActivityManifestMapper {
    private final String bucket;

    public ActivityManifestMapper(@Value("${manifest.canonical.bucket}") String bucket) {
        this.bucket = bucket;
    }

    public String toS3Key(ActivityManifestHandle handle, FqId fqid, Integer version) {
        if (!fqid.isActivityFqId())
            throw new IllegalArgumentException("FqId must be an activity FqId");

        return String.format("activities/%s/%s/%s/v%d/%s.json",
                fqid.getUnitId(),
                fqid.getEpisodeId(),
                fqid.getActivityId(),
                version,
                handle.value());
    }

    public String toS3URL(ActivityManifestHandle handle, FqId fqid, Integer version) {
        return String.format("s3://%s/%s", bucket, toS3Key(handle, fqid, version));
    }

    public ActivityManifestHandle fromS3KeyOrURL(String s) {
        String file = s.contains("/") ? s.substring(s.lastIndexOf('/') + 1) : s;
        String id = file.endsWith(".json") ? file.substring(0, file.length() - 5) : file;
        return ActivityManifestHandle.of(id);
    }
}
