package com.ssafy.completionism.api.controller.transaction.response;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class TransactionCategoryResponse {

    private Category category;

    private int cost;

    private boolean plus;

    @Builder
    public TransactionCategoryResponse(Category category, int cost, boolean plus) {
        this.category = category;
        this.cost = cost;
        this.plus = plus;
    }
}
