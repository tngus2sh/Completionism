package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddDiaryRequest {
    private Category category;

    private int cost;

    private String desc;

    private LocalDateTime createdDate;

    @Builder
    public AddDiaryRequest(Category category, int cost, String desc, LocalDateTime createdDate) {
        this.category = category;
        this.cost = cost;
        this.desc = desc;
        this.createdDate = createdDate;
    }


}
