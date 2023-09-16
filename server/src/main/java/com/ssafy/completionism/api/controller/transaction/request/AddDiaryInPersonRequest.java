package com.ssafy.completionism.api.controller.transaction.request;

import lombok.Builder;
import lombok.Data;

@Data
public class AddDiaryInPersonRequest {

    private String time;

    private String diary;

    private String feel;

    @Builder
    public AddDiaryInPersonRequest(String time, String diary, String feel) {
        this.time = time;
        this.diary = diary;
        this.feel = feel;
    }
}
