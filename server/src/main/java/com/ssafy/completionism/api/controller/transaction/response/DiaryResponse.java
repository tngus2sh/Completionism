package com.ssafy.completionism.api.controller.transaction.response;

import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DiaryResponse {

    private Long id;

    private String diary;

    private Feel feel;

    @Builder
    public DiaryResponse(Long id, String diary, Feel feel) {
        this.id = id;
        this.diary = diary;
        this.feel = feel;
    }
}
