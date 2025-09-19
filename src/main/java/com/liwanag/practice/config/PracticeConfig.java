package com.liwanag.practice.config;

import com.liwanag.practice.application.*;
import com.liwanag.practice.ports.primary.*;
import com.liwanag.practice.ports.secondary.CanonicalManifestStore;
import com.liwanag.practice.ports.secondary.CanonicalStore;
import com.liwanag.practice.ports.secondary.QuestionManifestStore;
import com.liwanag.practice.ports.secondary.SessionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PracticeConfig {
    @Bean
    public GetNextQuestion getNextQuestion(
            SessionStore sessionStore,
            QuestionManifestStore questionManifestStore
    ) {
        return new GetNextQuestionService(sessionStore, questionManifestStore);
    }

    @Bean
    public ManageSession manageSession(
            CanonicalStore canonicalStore,
            CanonicalManifestStore canonicalManifestStore,
            QuestionManifestStore questionManifestStore,
            QuestionPoolPolicy questionPoolPolicy,
            Personalization personalization,
            SessionStore sessionStore
    ) {
        return new ManageSessionService(
                canonicalStore,
                canonicalManifestStore,
                questionManifestStore,
                questionPoolPolicy,
                personalization,
                sessionStore
        );
    }

    @Bean
    public Personalization personalization() {
        return new PersonalizationService();
    }

    @Bean
    public QuestionPoolPolicy questionPoolPolicy() {
        return new QuestionPoolPolicyService();
    }

    @Bean
    public SubmitAnswer submitAnswer(
            SessionStore sessionStore,
            QuestionManifestStore questionManifestStore
    ) {
        return new SubmitAnswerService(
                sessionStore,
                questionManifestStore
        );
    }
}
