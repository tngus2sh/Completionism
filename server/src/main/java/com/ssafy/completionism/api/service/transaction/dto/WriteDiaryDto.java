package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

@Data
public class WriteDiaryDto {

    private String content;
    private Feel feel;

    @Builder
    private WriteDiaryDto(String content, Feel feel) {
        this.content = content;
        this.feel = feel;
    }
}
