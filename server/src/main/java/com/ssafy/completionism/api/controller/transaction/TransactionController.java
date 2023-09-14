package com.ssafy.completionism.api.controller.transaction;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.transaction.request.AddTransactionRequest;
import com.ssafy.completionism.api.controller.transaction.request.WriteOneLineDiaryRequest;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.api.service.transaction.TransactionService;
import com.ssafy.completionism.api.service.transaction.dto.AddTransactionDto;
import com.ssafy.completionism.api.service.transaction.dto.WriteDiaryDto;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * 한 줄 일기 작성
     */
    @PostMapping("/{transactionId}/diary")
    public ApiResponse<TransactionResponse> writeDiary(@RequestBody @Valid WriteOneLineDiaryRequest request,
                                                       @PathVariable Long transactionId) {
        String loginId = SecurityUtils.getCurrentLoginId();
        log.debug("[한 줄 일기 작성 Controller]");
        WriteDiaryDto dto = request.toDto();
        TransactionResponse response = transactionService.writeDiary(loginId, transactionId, dto);
        if (response == null) {
            return ApiResponse.of(1, HttpStatus.BAD_REQUEST, "본인의 거래내역이 아닙니다.", null);
        }
        return ApiResponse.ok(response);
    }

    /**
     * 거래내역 등록
     */
    @PostMapping
    public ApiResponse<String> addTransaction(@RequestBody @Valid AddTransactionRequest request) {
        String loginId = SecurityUtils.getCurrentLoginId();
        AddTransactionDto dto = request.of();
        Long savedTransactionId = transactionService.addTransaction(dto, loginId, LocalDateTime.now());

        return ApiResponse.ok("등록 성공");
    }
}
