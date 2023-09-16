package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StatisticsResponse {

    private int income;

    private int outcome;

    @Builder
    public StatisticsResponse(int income, int outcome) {
        this.income = income;
        this.outcome = outcome;
    }
}
