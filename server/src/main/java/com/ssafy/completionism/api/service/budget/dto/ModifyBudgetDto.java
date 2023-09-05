package com.ssafy.completionism.api.service.budget.dto;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ModifyBudgetDto {

    private LocalDate yearMonth;

    private int totalBudget;

    private Category category;

    @Builder
    public ModifyBudgetDto(LocalDate yearMonth, int totalBudget, Category category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
