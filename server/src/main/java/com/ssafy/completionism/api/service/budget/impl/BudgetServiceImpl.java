package com.ssafy.completionism.api.service.budget.impl;

import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.api.service.budget.dto.AddBudgetDto;
import com.ssafy.completionism.api.service.budget.dto.ModifyBudgetDto;
import com.ssafy.completionism.domain.budget.Budget;
import com.ssafy.completionism.domain.budget.repository.BudgetQueryRepository;
import com.ssafy.completionism.domain.budget.repository.BudgetRepository;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    /**
     * 예산 등록
     * @param loginId 사용자 아이디
     * @param dto 예산 등록 dto
     * @return 예산 식별키
     */
    @Override
    public Long addBudget(String loginId, AddBudgetDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Budget budget = Budget.toBudget(member, dto.getYearMonth(), dto.getTotalBudget(), dto.getCategory());
        Budget savedBudget = budgetRepository.save(budget);
        return savedBudget.getId();
    }

    /**
     * 해당 사용자의 매달 예산 비용 리스트
     * @param loginId 사용자 아이디
     * @return 예산 비용 리스트
     */
    @Override
    public List<MonthBudgetResponse> searchMonthAll(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        return budgetQueryRepository.getMonthBudgetAll(member.getId());
    }

    /**
     * 해당 사용자의 해당 기간 매달 예산 비용 리스트
     * @param loginId 사용자 아이디
     * @param startMonth 기간 시작월
     * @param endMonth 기간 끝월
     * @return 해당 사용자의 해당 기간 매달 예산 비용 리스트
     */
    @Override
    public List<MonthBudgetResponse> searchMonth(String loginId, LocalDate startMonth, LocalDate endMonth) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        return budgetQueryRepository.getMonthBudget(member.getId(), startMonth, endMonth);
    }

    /**
     * 해당 사용자의 예산 수정
     * 년월, 예산, 에산의 카테고리(목록)
     * @param loginId 사용자 아이디
     * @param dto 수정할 예산 정보
     */
    @Override
    public void modifyBudget(String loginId, ModifyBudgetDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        // 목표 예산 수정
        budgetQueryRepository.findByYearMonthAndMember(dto.getYearMonth(), member)
                .ifPresent((budget) -> {
                    budget.updateBudget(dto.getTotalBudget(), dto.getCategory());
        });
    }
}
