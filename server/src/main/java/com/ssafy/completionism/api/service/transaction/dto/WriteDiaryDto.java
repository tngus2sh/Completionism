package com.ssafy.completionism.api.service.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class WriteDiaryDto {

    private String content;

    @Builder
    private WriteDiaryDto(String content) {
        this.content = content;
    }
}
