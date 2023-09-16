package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class HistoryListResponse {
    private String startDay;
    private String endDay;
    private int income;
    private int spend;

    private List<HistoryResponse> day;

    @Builder
    private HistoryListResponse(LocalDateTime startDay, LocalDateTime endDay, int income, int spend, List<HistoryResponse> day) {
        this.startDay = startDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDay = endDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.income = income;
        this.spend = spend;
        this.day = day;
    }
}
