package com.liwanag.practice.adapters.secondary;

import com.liwanag.practice.adapters.secondary.persistence.entity.SessionEntity;
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
    private final DynamoDbTable<SessionEntity> sessionTable;
}
