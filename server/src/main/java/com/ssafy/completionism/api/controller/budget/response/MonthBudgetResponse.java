package com.ssafy.completionism.api.controller.budget.response;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class MonthBudgetResponse {

    private Long id;

    private Long memberId;

    private LocalDate yearMonth;

    private int totalBudget;

    private Category category;

    @Builder
    public MonthBudgetResponse(Long id, Long memberId, LocalDate yearMonth, int totalBudget, Category category) {
        this.id = id;
        this.memberId = memberId;
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
