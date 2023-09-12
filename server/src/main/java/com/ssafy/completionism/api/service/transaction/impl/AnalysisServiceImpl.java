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

    private final MemberRepository memberRepository;
    private final TransactionQueryRepository transactionQueryRepository;

    /**
     * 지출 많이 한 순서대로 정렬
     * @param loginId 사용자 아이디
     * @param resultDate 지출 조회 날짜
     * @return 거래내역
     */
    public TransactionListResponse getTransactions(String loginId, LocalDateTime resultDate) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        List<TransactionResponse> transactionsOneDay = transactionQueryRepository.getTransactionsOneDay(member, resultDate);

        String startDay = resultDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return TransactionListResponse.builder()
                .day(startDay)
                .transactions(transactionsOneDay)
                .build();
    }

    /**
     * 과소비 지수 측정
     * @param loginId 사용자 아이디
     * @param resultDate 조회 날짜
     * @return 과소비 지수에 따른 문구 출력
     */
    public String getOverConsumptionRate(String loginId, LocalDateTime resultDate) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        return null;
    }

    @Scheduled(cron = "* * 12 * * ?")
    private void showOneDayStatistics() {

    }
}
