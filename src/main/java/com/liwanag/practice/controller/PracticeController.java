package com.liwanag.practice.controller;

import com.liwanag.practice.dto.next.ClaimNext;
import com.liwanag.practice.service.PracticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/activities/{unitId}/{episodeId}/{activityId}")
@RequiredArgsConstructor
public class PracticeController {
    private final PracticeService practiceService;

    @PostMapping("/next")
    public ResponseEntity<?> next(@PathVariable String unitId, @PathVariable String episodeId, @PathVariable String activityId, @RequestHeader(name = "x-cognito-sub") UUID userId) {
        ClaimNext next = practiceService.claimNext(userId, unitId, episodeId, activityId);

        return switch (next.getSession().getStatus()) {
            case OK -> ResponseEntity.ok(next);
            case IN_PROGRESS -> ResponseEntity.status(409).body(next);
            case COMPLETED -> ResponseEntity.noContent().build();
        };
    }
}
