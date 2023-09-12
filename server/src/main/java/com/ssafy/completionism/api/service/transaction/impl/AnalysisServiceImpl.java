package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.controller.transaction.response.TransactionListResponse;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.api.service.transaction.AnalysisService;
import com.ssafy.completionism.api.service.transaction.dto.OneMonthIncomeExpenseDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.TransactionQueryRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final MemberRepository memberRepository;
    private final TransactionQueryRepository transactionQueryRepository;
    private final HistoryQueryRepository historyQueryRepository;

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
     * 과소비 지수 = (월 평균 수입 - 월 평균 저축) / 월 평균 수입
     * @param loginId 사용자 아이디
     * @param resultDate 조회 날짜
     * @return 과소비 지수에 따른 문구 출력
     */
    public String getOverConsumptionRate(String loginId, LocalDateTime resultDate) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Optional<OneMonthIncomeExpenseDto> oneMonthIncomeExpense = historyQueryRepository.getOneMonthIncomeExpense(loginId, resultDate);
        if (oneMonthIncomeExpense.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "한달 간의 입출금 내역을 찾을 수가 없습니다.");
        }

        int income = oneMonthIncomeExpense.get().getIncome();
        int outcome = oneMonthIncomeExpense.get().getOutcome();

        double overConsumption = (double)(income - outcome) / (double)income;

        if (overConsumption == 1) {
            return "심각한 과소비 상태입니다.";
        } else if (overConsumption >= 0.7) {
            return "과소비 상태입니다.";
        } else if (overConsumption >= 0.6) {
            return "적정 소비 수준입니다.";
        } else {
            return "알뜰 소비입니다.";
        }
    }

    @Scheduled(cron = "* * 12 * * ?")
    private void showOneDayStatistics() {

    }
}
