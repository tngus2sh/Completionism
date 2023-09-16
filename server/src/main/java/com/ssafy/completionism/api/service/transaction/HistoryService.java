package com.ssafy.completionism.api.service.transaction;

import com.ssafy.completionism.api.controller.transaction.response.HistoryListResponse;
import com.ssafy.completionism.api.controller.transaction.response.HistoryResponse;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryQueryRepository historyQueryRepository;
    private final MemberRepository memberRepository;

    public List<TransactionResponse> getTransactionList(String loginId, LocalDate searchDay) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        return historyQueryRepository.getTransactionList(loginId, searchDay);
    }

    public HistoryListResponse getHistoryListUsingPeriod(String loginId, HistoryPeriodSearchCond cond) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        List<HistoryResponse> list = historyQueryRepository.getHistoryResponseForPeriod(loginId, cond);
        log.debug("[기간조회 서비스] 리스트 불러옴 사이즈 = {}", list.size());
        int incomeSum = 0;
        int outcomeSum = 0;

        for (HistoryResponse history : list) {
            incomeSum += history.getIncome();
            outcomeSum += history.getSpend();
        }
        log.debug("[기간조회 서비스] in = {}, out = {}", incomeSum, outcomeSum);
        if(list.size() > 0) {
            log.debug("[기간조회 서비스] list = {}", list.get(0).getFeel());
        }
        return getHistoryListResponse(cond, list, incomeSum, outcomeSum);
    }

    private static HistoryListResponse getHistoryListResponse(HistoryPeriodSearchCond cond, List<HistoryResponse> list, int incomeSum, int outcomeSum) {
        return HistoryListResponse.builder()
                .startDay(cond.getStartDay())
                .endDay(cond.getEndDay())
                .income(incomeSum)
                .spend(outcomeSum)
                .day(list)
                .build();
    }
}
