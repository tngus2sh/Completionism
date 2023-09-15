package com.ssafy.completionism.domain.transaction;

import lombok.Getter;

@Getter
public enum Feel {

    DESIRE(""),
    GRATITUDE(""),    JOY(""),
    ANGER(""),
    DISGUST(""),
    FEAR(""),
    GRIEF(""),
    CURIOSITY(""),
    SURPRISE(""),
    NEUTRAL(""),
    HAPPY("행복"),
    SAD("슬픔");

    private final String text;

    Feel(String text) {
        this.text = text;
    }
}
