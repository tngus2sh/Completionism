package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddDiaryRequest {
    private String time;

    private int cost;

    private String category;

    private String place;

    private String diary;

    private String feel;

    private boolean plus;

    @Builder
    public AddDiaryRequest(String time, int cost, String category, String place, String diary, String feel, boolean plus) {
        this.time = time;
        this.cost = cost;
        this.category = category;
        this.place = place;
        this.diary = diary;
        this.feel = feel;
        this.plus = !plus;
    }
}
