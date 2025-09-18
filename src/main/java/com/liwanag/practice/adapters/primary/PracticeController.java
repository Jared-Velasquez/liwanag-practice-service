package com.liwanag.practice.adapters.primary;

import com.liwanag.practice.domain.dto.session.StartSessionRequest;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.ports.primary.GetNextQuestion;
import com.liwanag.practice.ports.primary.ManageSession;
import com.liwanag.practice.ports.primary.SubmitAnswer;
import com.liwanag.practice.utils.Validators;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/practice")
@RequiredArgsConstructor
public class PracticeController {
    private final ManageSession sessionService;
    private final GetNextQuestion nextService;
    private final SubmitAnswer evaluationService;

    @PostMapping("/session/start")
    public ResponseEntity<?> startSession(
            @RequestHeader(name = "x-cognito-sub") UUID userId,
            @Valid @RequestBody StartSessionRequest body) {
        // TODO: handle errors such as session has already been created for this activity
        return ResponseEntity.ok(
                sessionService.startSession(
                        userId,
                        new FqId(body.getUnitId(), body.getEpisodeId(), body.getActivityId()
                        )
                )
        );
    }

    @PostMapping("/session/{sessionId}/next")
    public ResponseEntity<?> getNextQuestion(
            @RequestHeader(name = "x-cognito-sub") UUID userId,
            @PathVariable UUID sessionId) {
        // TODO: handle errors such as session not found, double-next, answer not provided
        // TODO: handle when there are no more questions (end session)

        return ResponseEntity.ok(nextService.claimNext(sessionId, userId));
    }

    @PostMapping("/session/{sessionId}/skip")
    public ResponseEntity<?> skipQuestion(
            @RequestHeader(name = "x-cognito-sub") UUID userId,
            @PathVariable UUID sessionId
    ) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/session/{sessionId}/answer")
    public ResponseEntity<?> answerQuestion(
            @RequestHeader(name = "x-cognito-sub") UUID userId,
            @PathVariable UUID sessionId
    ) {
        return ResponseEntity.noContent().build();
    }
}
