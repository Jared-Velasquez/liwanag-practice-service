package com.liwanag.practice.adapters.secondary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liwanag.practice.domain.model.content.*;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.secondary.CanonicalStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.liwanag.practice.utils.ContentKeys.activityPk;
import static com.liwanag.practice.utils.ContentKeys.liveSk;

@Component
@RequiredArgsConstructor
@Slf4j
public class DynamoDbCanonicalStore implements CanonicalStore {
    // Note: all canonical content is stored in one "ContentTable" for faster lookups
    private final DynamoDbTable<Unit> unitTable;
    private final DynamoDbTable<Episode> episodeTable;
    private final DynamoDbTable<Activity> activityTable;
    private final S3Client s3Client;

    @Override
    public List<Question> loadQuestions(FqId fqid) throws NoSuchElementException {
        // Load the DynamoDB activity entity by its fully qualified activity id
        Activity activity = Optional.ofNullable(
                activityTable.getItem(
                        Key.builder().partitionValue(activityPk(fqid)).sortValue(liveSk()).build()
                )
        ).orElseThrow();

        // Then take the manifestS3Key from the Activity entity and load the canonical questions JSON from S3
        URI manifestURI = URI.create(activity.getManifestS3Key());

        ActivityManifest manifest = null;
        try {
            var is = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(manifestURI.getHost())
                    .key(manifestURI.getPath().substring(1))
                    .build()
            );

             manifest = new ObjectMapper().readValue(is, ActivityManifest.class);
        } catch (Exception e) {
            log.error("Exception occurred when loading manifest", e);
            throw new NoSuchElementException("Could not load manifest");
        }

        return manifest.questions();
    }
}
