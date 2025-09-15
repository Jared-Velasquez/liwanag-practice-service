package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.adapters.secondary.persistence.entity.ActivityEntity;
import com.liwanag.practice.domain.model.content.Activity;
import com.liwanag.practice.domain.model.content.FqId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ActivityMapper {
    private final ActivityManifestMapper manifestMapper;
    // Note: no need to map toEntity since canonical content is read-only
    public Activity toModel(ActivityEntity entity) {

        return Activity.builder()
                .fqid(new FqId(entity.getPk()))
                .title(entity.getTitle())
                .manifestHandle(manifestMapper.fromS3KeyOrURL(entity.getManifestS3Key()))
                .totalQuestions(entity.getTotalQuestions())
                .build();
    }
}
