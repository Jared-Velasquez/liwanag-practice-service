package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.domain.model.content.Activity;
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
}
