package com.liwanag.practice.adapters.secondary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liwanag.practice.adapters.secondary.persistence.entity.ActivityEntity;
import com.liwanag.practice.adapters.secondary.persistence.entity.EpisodeEntity;
import com.liwanag.practice.adapters.secondary.persistence.entity.UnitEntity;
import com.liwanag.practice.adapters.secondary.persistence.mapper.ActivityMapper;
import com.liwanag.practice.domain.model.content.*;
import com.liwanag.practice.domain.model.questions.Question;
import com.liwanag.practice.ports.secondary.CanonicalStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Slf4j
public class DynamoDbCanonicalStore implements CanonicalStore {
    // Note: all canonical content is stored in one "ContentTable" for faster lookups
    private final DynamoDbTable<UnitEntity> unitTable;
    private final DynamoDbTable<EpisodeEntity> episodeTable;
    private final DynamoDbTable<ActivityEntity> activityTable;
    private final ActivityMapper activityMapper;

    public DynamoDbCanonicalStore(
            @Qualifier("unitTable") DynamoDbTable<UnitEntity> unitTable,
            @Qualifier("episodeTable") DynamoDbTable<EpisodeEntity> episodeTable,
            @Qualifier("activityTable") DynamoDbTable<ActivityEntity> activityTable,
            ActivityMapper activityMapper
    ) {
        this.unitTable = unitTable;
        this.episodeTable = episodeTable;
        this.activityTable = activityTable;
        this.activityMapper = activityMapper;
    }

    @Override
    public Activity loadActivity(FqId fqid) throws NoSuchElementException {
        ActivityEntity entity = Optional.ofNullable(
                activityTable.getItem(
                        Key.builder().partitionValue(activityPk(fqid)).sortValue(liveSk()).build()
                )
        ).orElseThrow(() -> {
            log.error("Activity not found for fqid: {}", fqid);
            return new NoSuchElementException("Activity not found");
        });

        return activityMapper.toModel(entity);
    }
}
