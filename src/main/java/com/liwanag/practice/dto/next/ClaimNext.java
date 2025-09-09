package com.liwanag.practice.dto.next;

import com.liwanag.practice.models.questions.Question;
import com.liwanag.practice.models.session.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimNext {
    private Source source;
    private UUID turnToken;
    private Session session;
    private Instant lease;
    private Question question;

    public enum Source {
        CANONICAL,
        PERSONALIZED
    }
}
