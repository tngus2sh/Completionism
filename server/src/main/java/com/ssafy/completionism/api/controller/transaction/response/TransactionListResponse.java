package com.ssafy.completionism.api.controller.transaction.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionListResponse {

    private String day;
    private List<TransactionResponse> transactions;
}
