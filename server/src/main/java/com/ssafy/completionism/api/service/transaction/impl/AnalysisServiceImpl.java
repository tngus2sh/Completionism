package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.controller.budget.response.BudgetCategoryResponse;
import com.ssafy.completionism.api.controller.budget.response.BudgetResponse;
import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;
import com.ssafy.completionism.api.controller.transaction.response.*;
import com.ssafy.completionism.api.service.gpt.GptService;
import com.ssafy.completionism.api.service.transaction.AnalysisService;
import com.ssafy.completionism.api.service.transaction.dto.OneMonthIncomeExpenseDto;
import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.budget.repository.BudgetQueryRepository;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.schedule.repository.ScheduleQueryRepository;
import com.ssafy.completionism.domain.transaction.StatisticsType;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.TransactionPeriodSearchCond;
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

        StringBuilder send = new StringBuilder();

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

        String budgetSend = "이번 달의 전체 예산은 " + budget.getTotalBudget() + " 원이야 \n";

        send.append(budgetSend);

        // 카테고리별 예산 가져오기
        List<BudgetCategoryResponse> budgetCategoryList = budgetQueryRepository.getBudgetCategory(member.getId(), nowFirstDate);

        for (BudgetCategoryResponse budgetCategoryResponse : budgetCategoryList) {
            String budgetCategorySend = budgetCategoryResponse.getCategory().getText() + "에 쓰이는 예산은 "
                    + budgetCategoryResponse.getTotalBudget() + "원을 쓸거라고 결정했어.\n";

            send.append(budgetCategorySend);
        }

        // 미래예상소비 가져오기
        int futureSchedule = 0;

        String futureSend = "나는 이번 달에 ";

        Optional<Integer> expenseOptional = scheduleQueryRepository.countExpenseMonthlyFutureSchedule(loginId, nowFirstDate);
        if (expenseOptional.isPresent()) {
            futureSchedule += expenseOptional.get();
            futureSend += expenseOptional.get() + "정도 지출할 계획이고, ";
        }

        Optional<Integer> incomeOptional = scheduleQueryRepository.countIncomeMonthlyFutureSchedule(loginId, nowFirstDate);
        if (incomeOptional.isPresent()) {
            futureSchedule += incomeOptional.get();
            futureSend += incomeOptional.get() + "정도 수입이 들어올 예정이야";
        }

        futureSend += " 그래서 총 " + futureSchedule + "원의 예상소비가 나와\n";

        send.append(futureSend);

        // 고정 지출 가져오기
        int fixedSchedule = 0;

        String fixedSend = "그리고 이번 달의 고정 소비는 ";

        Optional<Integer> expenseFixedOptional = scheduleQueryRepository.countExpenseMonthlyFixedSchedule(loginId, nowFirstDate);
        if (expenseFixedOptional.isPresent()) {
            fixedSchedule += expenseFixedOptional.get();
            fixedSend += expenseFixedOptional.get() + "원의 돈이 나가.";
        }

        Optional<Integer> incomeFixedOptional = scheduleQueryRepository.countIncomeMonthlyFixedSchedule(loginId, nowFirstDate);
        if (incomeFixedOptional.isPresent()) {
            fixedSchedule += incomeFixedOptional.get();
            fixedSend += incomeFixedOptional.get() + "원의 수입이 들어와. ";
        }

        fixedSend += "총 " + fixedSchedule + "원의 고정 소비가 나와\n";

        send.append(fixedSend);

        // 현재까지 한 달 지출, 수입 내역 가져오기
        int monthlyIncome = 0;
        int monthlyOutcome = 0;
        Optional<SumIncomeOutcomeResponse> sumIncomeOutcomeResponse  = historyQueryRepository.getSumIncomeOutcomeForPeriod(loginId, HistoryPeriodSearchCond.builder()
                .startDay(nowFirstDate)
                .endDay(now)
                .build());

        if (sumIncomeOutcomeResponse.isPresent()) {
            monthlyOutcome += sumIncomeOutcomeResponse.get().getOutcome();
            monthlyIncome += sumIncomeOutcomeResponse.get().getIncome();

            String monthlySend = "현재까지 나는 총 " + monthlyOutcome + "원의 지출을 했고, "
                    +monthlyIncome + "원의 돈이 들어왔어.\n";

            send.append(monthlySend);
        }

        //  카테고리별 한 달 지출, 수입 내역 가져오기
        StringBuilder categorySend = new StringBuilder();
        categorySend.append("카테고리별 한 달 지출, 수입 내역을 얘기하자면 ");
        List<TransactionCategoryResponse> costCategoryList = transactionQueryRepository.getIncomeForCategory(member, TransactionPeriodSearchCond.builder()
                .startDay(nowFirstDate)
                .endDay(now)
                .build());

        for (TransactionCategoryResponse costCategory : costCategoryList) {
            if (costCategory.isPlus()) {
                categorySend.append(costCategory.getCategory().getText() + "에서 "
                        + "총 " + costCategory.getCost() + "원의 수입이 있었어.\n");
            } else {
                categorySend.append(costCategory.getCategory().getText() + "에서 "
                        + "총  " + costCategory.getCost() + "원의 지출이 있었어.\n");
            }
        }

        if (costCategoryList.size() == 0) {
            categorySend.append("아직 없어.\n");
        }
        send.append(categorySend);

        send.append("여기까지 나의 한달 간 소비 내역이야. 현재 내 소비 패턴에 대해서 피드백을 친근한 말투로 존댓말을 써서 짧게 한 줄로 말해줘.");

        GPTCompletionChatResponse gptAnswer = gptService.completionChat(GPTCompletionChatRequest.builder()
                        .role("user")
                        .message(send.toString())
                .build());

        String feedback  = gptAnswer.getMessages().get(0).getMessage();

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
            SumIncomeOutcomeResponse statistics = historyQueryRepository.getSumIncomeOutcomeForPeriod(loginId
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
            SumIncomeOutcomeResponse statistics = historyQueryRepository.getSumIncomeOutcomeForPeriod(loginId
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
            SumIncomeOutcomeResponse statistics = historyQueryRepository.getSumIncomeOutcomeForPeriod(loginId
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
