package com.liwanag.practice.application;

import com.liwanag.practice.domain.dto.ClaimNext;
import com.liwanag.practice.ports.primary.GetNextQuestion;

import java.util.UUID;

public class GetNextQuestionService implements GetNextQuestion {

    @Override
    public ClaimNext claimNext(UUID userId, UUID SessionId) {
        return null;
    }
}
