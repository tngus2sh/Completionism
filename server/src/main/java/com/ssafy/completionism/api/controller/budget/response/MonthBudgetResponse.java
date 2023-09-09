package com.ssafy.completionism.api.controller.budget.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class MonthBudgetResponse {

    private String id;

    private String memberId;

    private String yearMonth;

    private int totalBudget;

    private String category;

    @Builder
    public MonthBudgetResponse(Long id, Long memberId, LocalDate yearMonth, int totalBudget, String category) {
        this.id = id + "";
        this.memberId = memberId + "";
        this.yearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
