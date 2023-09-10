package com.ssafy.completionism.domain.transaction;

import lombok.Getter;

@Getter
public enum Feel {

    HAPPY("행복"),
    SAD("슬픔");

    private final String text;

    Feel(String text) {
        this.text = text;
    }
}
