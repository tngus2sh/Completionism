package com.ssafy.completionism.api.service.budget.impl;

import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.domain.budget.repository.BudgetQueryRepository;
import com.ssafy.completionism.domain.budget.repository.BudgetRepository;
import com.ssafy.completionism.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetQueryRepository budgetQueryRepository;

//    private final MemberRepository memberRepository;

    /**
     * 해당 사용자의 매달 예산 비용 리스트
     * @param phone 사용자 전화번호
     * @return 예산 비용 리스트
     */
    @Override
    public List<MonthBudgetResponse> searchMonthAll(String phone) {
//        Member member = memberRepository.findByPhone(phone).orElseThrow(NoSuchElementException::new);
        Member member = null;

        return budgetQueryRepository.getMonthBudgetAll(member.getId());
    }

    /**
     * 해당 사용자의 해당 기간 매달 예산 비용 리스트
     * @param phone 사용자 전화번호
     * @param startMonth 기간 시작월
     * @param endMonth 기간 끝월
     * @return 해당 사용자의 해당 기간 매달 예산 비용 리스트
     */
    @Override
    public List<MonthBudgetResponse> searchMonth(String phone, LocalDate startMonth, LocalDate endMonth) {
//        Member member = memberRepository.findByPhone(phone).orElseThrow(NoSuchElementException::new);
        Member member = null;

        return budgetQueryRepository.getMonthBudget(member.getId(), startMonth, endMonth);
    }
}
