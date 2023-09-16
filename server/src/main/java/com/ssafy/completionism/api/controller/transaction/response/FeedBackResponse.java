package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FeedBackResponse {

    private int income;

    private int outcome;

    private String type;

    @Builder
    public FeedBackResponse(int income, int outcome, String type) {
        this.income = income;
        this.outcome = outcome;
        this.type = type;
    }
}
