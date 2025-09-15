package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.adapters.secondary.persistence.entity.SessionEntity;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.session.Session;
import com.liwanag.practice.utils.SessionKeys;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public final class SessionMapper {
    public SessionEntity toEntity(Session model) {
        SessionManifestMapper sessionManifestMapper = new SessionManifestMapper("test-bucket");

        return SessionEntity.builder()
                .pk(SessionKeys.sessionPk(model.getUserId()))
                .sk(SessionKeys.sessionSk(model.getSessionId()))
                .activityFqId(model.getActivityFqId().toString())
                .activityVersion(model.getActivityVersion())
                .status(model.getStatus().toString())
                .currentIndex(model.getCurrentIndex())
                .turnToken(model.getTurnToken().toString())
                .leaseExpiresAt(model.getLeaseExpiresAt().toEpochMilli())
                .manifestS3Key(sessionManifestMapper.toS3URL(model.getManifestHandle()))
                .attempted(model.getAttempted())
                .correct(model.getCorrect())
                .createdAt(model.getCreatedAt().toEpochMilli())
                .updatedAt(model.getUpdatedAt().toEpochMilli())
                .completedAt(model.getCompletedAt().toEpochMilli())
                .build();
    }

    public Session toModel(SessionEntity entity) {
        SessionManifestMapper sessionManifestMapper = new SessionManifestMapper("test-bucket");

        return Session.builder()
                .sessionId(SessionKeys.extractSessionId(entity.getSk()))
                .userId(SessionKeys.extractUserId(entity.getPk()))
                .activityFqId(new FqId(entity.getActivityFqId()))
                .activityVersion(entity.getActivityVersion())
                .status(Session.Status.valueOf(entity.getStatus())) // TODO: perform exception handling
                .currentIndex(entity.getCurrentIndex())
                .turnToken(UUID.fromString(entity.getTurnToken()))
                .leaseExpiresAt(Instant.ofEpochMilli(entity.getLeaseExpiresAt()))
                .manifestHandle(sessionManifestMapper.fromS3KeyOrURL(entity.getManifestS3Key()))
                .attempted(entity.getAttempted())
                .correct(entity.getCorrect())
                .createdAt(Instant.ofEpochMilli(entity.getCreatedAt()))
                .updatedAt(Instant.ofEpochMilli(entity.getUpdatedAt()))
                .completedAt(Instant.ofEpochMilli(entity.getCompletedAt()))
                .build();
    }
}
