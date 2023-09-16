package com.ssafy.completionism.api.controller.transaction.request;

import lombok.Builder;
import lombok.Data;

@Data
public class AddDiaryRequest {
    private String category; // 카테고리

    private int cost; // 비용

    private String desc; // 한 줄 일기

    private String createdDate; // 거래내역 날짜

    private String feel;

    @Builder
    public AddDiaryRequest(String category, int cost, String desc, String createdDate, String feel) {
        this.category = category;
        this.cost = cost;
        this.desc = desc;
        this.createdDate = createdDate;
        this.feel = feel;
    }
}
