package com.ssafy.completionism.api.controller.budget.response;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BudgetCategoryResponse {

    private Category category;
    private int totalBudget;


    @Builder
    public BudgetCategoryResponse(Category category, int totalBudget) {
        this.category = category;
        this.totalBudget = totalBudget;
    }
}
