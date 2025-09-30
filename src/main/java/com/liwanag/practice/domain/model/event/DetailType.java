package com.liwanag.practice.domain.model.event;

public enum DetailType {
    ANSWER_EVALUATED("AnswerEvaluated"),
    SESSION_FINISHED("SessionFinished");

    private final String source;

    DetailType(String s) {
        this.source = s;
    }

    @Override
    public String toString() {
        return this.source;
    }
}
