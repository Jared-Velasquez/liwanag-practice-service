package com.liwanag.practice.service;

import com.liwanag.practice.dto.next.ClaimNext;
import com.liwanag.practice.dto.next.ClaimNext.Source;
import com.liwanag.practice.models.questions.MultipleChoiceQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PracticeService {
    @Transactional
    public void claimNext() {
        return;
    }

    @Transactional
    public void answer() {
        return;
    }

}
