package com.liwanag.practice.ports.primary;

import com.liwanag.practice.domain.dto.ClaimNextDTO;

import java.util.UUID;

public interface GetNextQuestion {
    ClaimNextDTO claimNext(UUID sessionId, UUID userId);
}
