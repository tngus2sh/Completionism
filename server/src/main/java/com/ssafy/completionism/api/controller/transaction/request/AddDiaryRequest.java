package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddDiaryRequest {
    private String category;

    private int cost;

    private String desc;

    private String createdDate;

    @Builder
    public AddDiaryRequest(String category, int cost, String desc, String createdDate) {
        this.category = category;
        this.cost = cost;
        this.desc = desc;
        this.createdDate = createdDate;
    }


}
