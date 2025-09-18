package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.adapters.secondary.persistence.entity.SessionEntity;
import com.liwanag.practice.adapters.secondary.persistence.mapper.SessionMapper;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.secondary.SessionStore;
import com.liwanag.practice.utils.SessionKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class DynamoDbSessionStore implements SessionStore {
    private final DynamoDbTable<SessionEntity> sessionTable;
    private final SessionMapper sessionMapper;

    public DynamoDbSessionStore(
            @Qualifier("sessionTable") DynamoDbTable<SessionEntity> sessionTable,
            SessionMapper sessionMapper
    ) {
        this.sessionTable = sessionTable;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public void save(Session session) {
        sessionTable.putItem(sessionMapper.toEntity(session));
    }

    @Override
    public Optional<Session> load(UUID sessionId, UUID userId) {
        SessionEntity entity = sessionTable.getItem(r -> r.key(k -> k
                .partitionValue(SessionKeys.sessionPk(userId))
                .sortValue(SessionKeys.sessionSk(sessionId)
                )));

        if (entity == null) {
            log.error("Session not found for userId: {} and sessionId: {}", userId, sessionId);
            return Optional.empty();
        }

        return Optional.of(sessionMapper.toModel(entity));
    }
}
