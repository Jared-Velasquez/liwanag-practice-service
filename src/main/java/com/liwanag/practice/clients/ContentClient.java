package com.liwanag.practice.clients;

import com.liwanag.practice.models.content.Activity;
import com.liwanag.practice.models.content.Episode;
import com.liwanag.practice.models.content.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

import static com.liwanag.practice.utils.ContentKeys.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContentClient {
    private final DynamoDbTable<Unit> unitTable;
    private final DynamoDbTable<Episode> episodeTable;
    private final DynamoDbTable<Activity> activityTable;

    // Note: when using DynamoDB key builder, both the partition and sort value must be passed
    public Optional<Unit> getUnit(String unitId) {
        return Optional.ofNullable(
                unitTable.getItem(
                        Key.builder().partitionValue(unitPk(unitId)).sortValue(liveSk()).build()
                )
        );
    }

    public Optional<Episode> getEpisode(String unitId, String episodeId) {
        return Optional.ofNullable(
                episodeTable.getItem(
                        Key.builder().partitionValue(episodePk(unitId, episodeId)).sortValue(liveSk()).build()
                )
        );
    }

    public Optional<Activity> getActivity(String unitId, String episodeId, String activityId) {
        return Optional.ofNullable(
                activityTable.getItem(
                        Key.builder().partitionValue(activityPk(unitId, episodeId, activityId)).sortValue(liveSk()).build()
                )
        );
    }
}
