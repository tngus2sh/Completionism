package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.controller.transaction.response.TransactionListResponse;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.api.service.transaction.AnalysisService;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.repository.TransactionQueryRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    // TODO : 지출 많이 한 순서대로 정렬, (한달 기준)과소비 지수

    private final MemberRepository memberRepository;
    private final TransactionQueryRepository transactionQueryRepository;

    public TransactionListResponse getTransactions(String loginId, LocalDateTime resultDate) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        List<TransactionResponse> transactionsOneDay = transactionQueryRepository.getTransactionsOneDay(member, resultDate);

        String startDay = resultDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return TransactionListResponse.builder()
                .day(startDay)
                .transactions(transactionsOneDay)
                .build();
    }

    @Scheduled(cron = "* * 12 * * ?")
    private void showOneDayStatistics() {

    }
}
