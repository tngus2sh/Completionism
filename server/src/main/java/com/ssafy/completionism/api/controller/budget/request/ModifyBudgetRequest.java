package com.ssafy.completionism.api.controller.budget.request;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ModifyBudgetRequest {

    @NotBlank
    private String yearMonth;

    @NotNull
    @Positive
    private int totalBudget;

    @NotBlank
    @Pattern(regexp = "^(TOTAL|TRAFFIC|FOOD|SHOPPING|LIFE|ETC)$")
    private String category;

    @Builder
    public ModifyBudgetRequest(String yearMonth, int totalBudget, String category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
