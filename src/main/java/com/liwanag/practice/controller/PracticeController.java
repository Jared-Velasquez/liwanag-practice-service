package com.liwanag.practice.controller;

import com.liwanag.practice.dto.next.NextRequest;
import com.liwanag.practice.service.PracticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities/{unitId}/{episodeId}/{activityId}")
@RequiredArgsConstructor
public class PracticeController {
    private final PracticeService practiceService;

    @PostMapping("/next")
    public ResponseEntity<?> next(@PathVariable String unitId, @PathVariable String episodeId, @PathVariable String activityId, @RequestBody NextRequest request) {
        // Implementation here
        return ResponseEntity.ok().build();
    }
}
