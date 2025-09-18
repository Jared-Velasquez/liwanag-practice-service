package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.dto.ClaimNext;
import com.liwanag.practice.domain.model.content.FqId;

import java.util.UUID;

public interface GetNextQuestion {
    ClaimNext claimNext(UUID sessionId, UUID userId);
}
