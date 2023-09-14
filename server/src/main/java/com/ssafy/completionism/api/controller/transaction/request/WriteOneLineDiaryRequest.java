package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

@Data
public class WriteOneLineDiaryRequest {

    private Feel feel;
    private String diary;

    @Builder
    private WriteOneLineDiaryRequest(Feel feel, String diary) {
        this.feel = feel;
        this.diary = diary;
    }
}
