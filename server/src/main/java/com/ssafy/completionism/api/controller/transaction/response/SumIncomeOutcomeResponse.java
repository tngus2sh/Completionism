package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SumIncomeOutcomeResponse {

    private int income;

    private int outcome;

    @Builder
    public SumIncomeOutcomeResponse(int income, int outcome) {
        this.income = income;
        this.outcome = outcome;
    }
}
