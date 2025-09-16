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

    public ActivityManifestMapper(@Value("${manifest.activity.bucket}") String bucket) {
        this.bucket = bucket;
    }

    public String toS3Key(ActivityManifestHandle handle) {
        return String.format("activities/%s/%s/%s/v%d/%s.json",
                handle.getFqId().getUnitId(),
                handle.getFqId().getEpisodeId(),
                handle.getFqId().getActivityId(),
                handle.getVersion(),
                handle.getId());
    }

    public String toS3URL(ActivityManifestHandle handle) {
        return String.format("s3://%s/%s", bucket, toS3Key(handle));
    }

    public ActivityManifestHandle fromS3KeyOrURL(String s) {
        // Note: the S3 key is in the format: activities/<unitId>/<episodeId>/<activityId>/v<version>/<manifestId>.json

        String key = s.startsWith("s3://") ? s.substring(s.indexOf('/', 5) + 1) : s;
        String[] parts = key.split("/");
        if (parts.length != 6 || !parts[0].equals("activities")) {
            throw new IllegalArgumentException("Invalid S3 key or URL format for ActivityManifestHandle");
        }
        String unitId = parts[1];
        String episodeId = parts[2];
        String activityId = parts[3];
        String versionPart = parts[4];
        String file = parts[5];

        if (!versionPart.startsWith("v") || !file.endsWith(".json")) {
            throw new IllegalArgumentException("Invalid S3 key or URL format for ActivityManifestHandle");
        }

        Integer version;
        try {
            version = Integer.parseInt(versionPart.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Version must be a positive integer", e);
        }
        if (version <= 0) {
            throw new IllegalArgumentException("Version must be a positive integer");
        }

        String id = file.substring(0, file.length() - 5); // remove .json
        FqId fqid;
        try {
            fqid = new FqId(String.format("ACTIVITY#%s#%s#%s", unitId, episodeId, activityId));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid FqId format extracted from S3 key or URL", e);
        }

        return ActivityManifestHandle.of(id, fqid, version);
    }
}
