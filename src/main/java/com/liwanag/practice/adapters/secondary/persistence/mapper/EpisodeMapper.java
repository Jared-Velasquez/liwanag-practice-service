package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.adapters.secondary.persistence.entity.EpisodeEntity;
import com.liwanag.practice.domain.model.content.Episode;
import com.liwanag.practice.domain.model.content.FqId;
import org.springframework.stereotype.Component;

@Component
public final class EpisodeMapper {
    // Note: no need to map toEntity since canonical content is read-only

    public Episode toModel(EpisodeEntity entity) {
        return Episode.builder()
                .fqid(new FqId(entity.getPk()))
                .title(entity.getTitle())
                .build();
    }
}
