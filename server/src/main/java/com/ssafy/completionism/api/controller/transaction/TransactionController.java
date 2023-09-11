package com.ssafy.completionism.api.controller.transaction;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.transaction.request.AddTransactionRequest;
import com.ssafy.completionism.api.service.transaction.TransactionService;
import com.ssafy.completionism.api.service.transaction.dto.AddTransactionDto;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import com.ssafy.completionism.security.SecurityUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ApiResponse<String> addTransaction(@RequestBody @Valid AddTransactionRequest request) {
        String loginId = SecurityUtils.getCurrentLoginId();
        AddTransactionDto dto = request.of();
        Long savedTransactionId = transactionService.addTransaction(dto, loginId, LocalDateTime.now());

        return ApiResponse.ok("등록 성공");
    }
}
