package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.model.content.FqId;

import java.util.UUID;

public interface QuestionPool {
    void generatePool(UUID userId, FqId fqId);
}
