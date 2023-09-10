package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryResponse {
    private LocalDate day;
    private int income;
    private int spend;
    private int feel;

    @Builder
    public HistoryResponse(LocalDate day, int income, int spend, int feel) {
        this.day = day;
        this.income = income;
        this.spend = spend;
        this.feel = feel;
    }
}
