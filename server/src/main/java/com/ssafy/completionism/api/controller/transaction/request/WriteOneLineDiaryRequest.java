package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.api.service.transaction.dto.WriteDiaryDto;
import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WriteOneLineDiaryRequest {

    private Feel feel;
    private String diary;

    @Builder
    private WriteOneLineDiaryRequest(String feel, String diary) {
        this.feel = Feel.valueOf(feel);
        this.diary = diary;
    }

    public WriteDiaryDto toDto() {
        return WriteDiaryDto.builder()
                .content(this.diary)
                .feel(this.feel)
                .build();
    }

}
