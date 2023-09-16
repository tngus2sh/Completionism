package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.budget.response.BudgetResponse;
import com.ssafy.completionism.api.controller.transaction.response.*;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.api.service.gpt.GptService;
import com.ssafy.completionism.api.service.transaction.AnalysisService;
import com.ssafy.completionism.api.service.transaction.dto.OneMonthIncomeExpenseDto;
import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.budget.Budget;
import com.ssafy.completionism.domain.budget.repository.BudgetQueryRepository;
import com.ssafy.completionism.domain.budget.repository.BudgetRepository;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.schedule.repository.ScheduleQueryRepository;
import com.ssafy.completionism.domain.transaction.StatisticsType;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.TransactionQueryRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private final BudgetQueryRepository budgetQueryRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final GptService gptService;


    @Override
    public AIFeedBackResponse getAIFeedBack(String loginId) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        // 현재 날짜 가져오기
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 달의 첫 날 == 해당 달의 예산
        LocalDate nowFirstDate = now.withDayOfMonth(1);

        // 현재 요일 가져오기
        DayOfWeek nowDay = now.getDayOfWeek();

        // 전체 예산 가져오기
        Optional<BudgetResponse> budgetOptional = budgetQueryRepository.findByYearMonthAndCategory(member.getId(), nowFirstDate, Category.TOTAL);

        if (budgetOptional.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "예산을 찾을 수 없습니다.");
        }
        BudgetResponse budget = budgetOptional.get();

        // 미래예상소비 가져오기
        int futureSchedule = 0;

        Optional<Integer> expenseOptional = scheduleQueryRepository.countExpenseMonthlyFutureSchedule(loginId, nowFirstDate);
        if (expenseOptional.isPresent()) {
            futureSchedule += expenseOptional.get();
        }

        Optional<Integer> incomeOptional = scheduleQueryRepository.countIncomeMonthlyFutureSchedule(loginId, nowFirstDate);
        if (incomeOptional.isPresent()) {
            futureSchedule += incomeOptional.get();
        }

        // 고정 지출 가져오기
        int fixedSchedule = 0;

        Optional<Integer> expenseFixedOptional = scheduleQueryRepository.countExpenseMonthlyFixedSchedule(loginId, nowFirstDate);
        if (expenseFixedOptional.isPresent()) {
            fixedSchedule += expenseFixedOptional.get();
        }

        Optional<Integer> incomeFixedOptional = scheduleQueryRepository.countIncomeMonthlyFixedSchedule(loginId, nowFirstDate);
        if (incomeFixedOptional.isPresent()) {
            fixedSchedule += incomeFixedOptional.get();
        }

        // 현재까지 한 달 지출, 수입 내역 가져오기


        //  카테고리별 지출, 수입 내역 가져오기


        return null;
    }

    /**
     * 한달 소비 통계
     * @param loginId 사용자 아이디
     * @param type 일별, 월별, 연별
     * @return 통계 결과
     */
    @Override
    public FeedBackResponse getFeedBack(String loginId, StatisticsType type) {
        memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if (type.equals(StatisticsType.DAY)) {
            StatisticsResponse statistics = historyQueryRepository.getHistoryResponseForPeriodStatistics(loginId
                    , HistoryPeriodSearchCond.builder()
                            .startDay(now.minusDays(1))
                            .endDay(now)
                            .build()).orElseThrow(NoSuchElementException::new);

            return FeedBackResponse.builder()
                    .income(statistics.getIncome())
                    .outcome(statistics.getOutcome())
                    .type(StatisticsType.DAY.name())
                    .build();

        } else if (type.equals(StatisticsType.MONTH)) {
            StatisticsResponse statistics = historyQueryRepository.getHistoryResponseForPeriodStatistics(loginId
                    , HistoryPeriodSearchCond.builder()
                            .startDay(now.minusMonths(1))
                            .endDay(now)
                            .build()).orElseThrow(NoSuchElementException::new);

            return FeedBackResponse.builder()
                    .income(statistics.getIncome())
                    .outcome(statistics.getOutcome())
                    .type(StatisticsType.MONTH.name())
                    .build();

        } else if (type.equals(StatisticsType.YEAR)) {
            StatisticsResponse statistics = historyQueryRepository.getHistoryResponseForPeriodStatistics(loginId
                    , HistoryPeriodSearchCond.builder()
                            .startDay(now.minusYears(1))
                            .endDay(now)
                            .build()).orElseThrow(NoSuchElementException::new);

            return FeedBackResponse.builder()
                    .income(statistics.getIncome())
                    .outcome(statistics.getOutcome())
                    .type(StatisticsType.YEAR.name())
                    .build();

        } else {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 통계를 찾을 수 없습니다.");
        }
    }

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
}
