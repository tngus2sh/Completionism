package com.ssafy.completionism.api.controller.transaction.response;

import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

@Data
public class TransactionResponse {

    private Long transactionId;
    private String time;
    private int cost;
    private boolean isPlus;
    private String category;
    private String place;
    private String diary;
    private Feel feel;

    @Builder
    public TransactionResponse(Long id, String time, int cost, boolean isPlus, String category, String place, String diary, Feel feel) {
        this.transactionId = id;
        this.time = time;
        this.cost = cost;
        this.isPlus = isPlus;
        this.category = category;
        this.place = place;
        this.diary = diary;
        this.feel = feel;
    }
}
