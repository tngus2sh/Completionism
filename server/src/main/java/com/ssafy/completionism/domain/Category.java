package com.ssafy.completionism.domain;

public enum Category {
    TRAFFIC("교통"),
    FOOD("식비"),
    SHOPPING("쇼핑"),
    LIFE("생활"),
    ETC("나머지");

    private final String text;

    Category(String text) {
        this.text = text;
    }
}
