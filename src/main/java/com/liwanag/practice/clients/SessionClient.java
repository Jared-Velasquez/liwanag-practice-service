package com.liwanag.practice.clients;

import com.liwanag.practice.domain.model.session.Session;
import static com.liwanag.practice.utils.SessionKeys.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionClient {
    private final DynamoDbTable<Session> sessionTable;

    public Optional<Session> getSession(String unitId, String episodeId, String activityId, UUID userId) {
        return Optional.ofNullable(
                sessionTable.getItem(
                        Key.builder().partitionValue(sessionPk(userId)).sortValue(sessionSk(unitId, episodeId, activityId)).build()
                )
        );
    }

    public void setSession(Session session) {
        sessionTable.putItem(session);
    }
}
