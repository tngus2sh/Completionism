package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponse {

    private String diary;

    private String feel;

    @Builder
    public DiaryResponse(String diary, String feel) {
        this.diary = diary;
        this.feel = feel;
    }
}
