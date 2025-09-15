package com.liwanag.practice.adapters.secondary.persistence.mapper;

import com.liwanag.practice.adapters.secondary.persistence.entity.UnitEntity;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.content.Unit;
import org.springframework.stereotype.Component;

@Component
public final class UnitMapper {
// Note: no need to map toEntity since canonical content is read-only
    public Unit toModel(UnitEntity entity) {
        return Unit.builder()
                .fqid(new FqId(entity.getPk()))
                .title(entity.getTitle())
                .description(entity.getDescription()).build();
    }
}
