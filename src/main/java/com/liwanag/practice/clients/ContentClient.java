package com.liwanag.practice.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liwanag.practice.domain.model.content.Activity;
import com.liwanag.practice.domain.model.content.ActivityManifest;
import com.liwanag.practice.domain.model.content.Episode;
import com.liwanag.practice.domain.model.content.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.liwanag.practice.utils.ContentKeys.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContentClient {
    private final DynamoDbTable<Unit> unitTable;
    private final DynamoDbTable<Episode> episodeTable;
    private final DynamoDbTable<Activity> activityTable;
    private final S3Client s3Client;

    // Note: when using DynamoDB key builder, both the partition and sort value must be passed

    // Use Caffeine caching for canonical content (reasoning is in CacheConfig).
    // Since Cacheable supports Optional, result always refers to the business entity and never a supported wrapper
    // https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html
    @Cacheable(cacheNames = "content", key = "#unitId", unless = "#result == null")
    public Optional<Unit> getUnit(String unitId) {
        return Optional.ofNullable(
                unitTable.getItem(
                        Key.builder().partitionValue(unitPk(unitId)).sortValue(liveSk()).build()
                )
        );
    }

    @Cacheable(cacheNames = "content", key = "#unitId#episodeId", unless = "#result == null")
    public Optional<Episode> getEpisode(String unitId, String episodeId) {
        return Optional.ofNullable(
                episodeTable.getItem(
                        Key.builder().partitionValue(episodePk(unitId, episodeId)).sortValue(liveSk()).build()
                )
        );
    }

    @Cacheable(cacheNames = "content", key = "#unitId#episodeId#activityId", unless = "#result == null")
    public Optional<Activity> getActivity(String unitId, String episodeId, String activityId) {
        return Optional.ofNullable(
                activityTable.getItem(
                        Key.builder().partitionValue(activityPk(unitId, episodeId, activityId)).sortValue(liveSk()).build()
                )
        );
    }

    @Cacheable(cacheNames = "manifests", key = "#unitId#episodeId#activityId", unless = "#result == null")
    public ActivityManifest loadActivityManifest(String unitId, String episodeId, String activityId) throws IOException {
        Activity activity = getActivity(unitId, episodeId, activityId).orElseThrow();
        URI manifestURI = URI.create(activity.getManifestS3Key());

        try {
            var is = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(manifestURI.getHost())
                    .key(manifestURI.getPath().substring(1))
                    .build()
            );

            return new ObjectMapper().readValue(is, ActivityManifest.class);
        } catch (Exception e) {
            log.error("Exception occurred when loading manifest", e);
            throw e;
        }
    }
}
