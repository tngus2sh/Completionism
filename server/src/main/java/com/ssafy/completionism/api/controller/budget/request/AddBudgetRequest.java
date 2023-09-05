package com.ssafy.completionism.api.controller.budget.request;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AddBudgetRequest {

    private LocalDate yearMonth;

    private int totalBudget;

    private Category category;

    @Builder
    public AddBudgetRequest(LocalDate yearMonth, int totalBudget, Category category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
