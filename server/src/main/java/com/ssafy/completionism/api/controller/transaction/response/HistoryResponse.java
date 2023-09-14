package com.ssafy.completionism.api.controller.transaction.response;

import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
public class HistoryResponse {
    private LocalDate day;
    private int income;
    private int spend;
    private Feel feel;

    @Builder
    public HistoryResponse(LocalDateTime day, int income, int spend, Feel feel) {
        this.day = day.toLocalDate();
        this.income = income;
        this.spend = spend;
        this.feel = feel;
    }
}
