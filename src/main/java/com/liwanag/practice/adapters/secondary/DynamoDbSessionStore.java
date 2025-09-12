package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.ports.secondary.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.UUID;

import static com.liwanag.practice.utils.SessionKeys.sessionPk;
import static com.liwanag.practice.utils.SessionKeys.sessionSk;

@Component
@RequiredArgsConstructor
@Slf4j
public class DynamoDbSessionStore implements SessionStore {
    private final DynamoDbTable<Session> sessionTable;

    @Override
    public Boolean isSessionActive(UUID userId, FqId fqid) {
        String pk = sessionPk(userId);
        String sk = sessionSk(fqid);
        Session session = sessionTable.getItem(r -> r.key(k -> k.partitionValue(pk).sortValue(sk)));
        return session != null;
    }

    @Override
    public void createSession(UUID userId, FqId fqid) {
        if (!fqid.isActivityFqId())
            throw new IllegalArgumentException("FqId is not an activity FqId");
        String pk = sessionPk(userId);
        String sk = sessionSk(fqid);
        Session session = Session.builder().pk(pk).sk(sk).activityFqId(fqid.toString()).status(Session.Status.IDLE).build();

        sessionTable.putItem(session);
    }
}
