package com.ssafy.completionism.api.controller.budget.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class AddBudgetRequest {

    @NotBlank
    private String yearMonth;

    @NotNull
    @Positive
    private int totalBudget;

    @NotBlank
    @Pattern(regexp = "^(TOTAL|TRAFFIC|FOOD|SHOPPING|LIFE|ETC)$")
    private String category;

    @Builder
    public AddBudgetRequest(String yearMonth, int totalBudget, String category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
