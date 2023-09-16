package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AIFeedBackResponse {
    private String feedback;

    @Builder
    public AIFeedBackResponse(String feedback) {
        this.feedback = feedback;
    }
}
