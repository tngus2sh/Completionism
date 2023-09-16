package com.ssafy.completionism.api.service.budget.impl;

import com.ssafy.completionism.api.controller.budget.response.BudgetResponse;
import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.api.service.budget.dto.AddBudgetDto;
import com.ssafy.completionism.api.service.budget.dto.ModifyBudgetDto;
import com.ssafy.completionism.domain.budget.Budget;
import com.ssafy.completionism.domain.budget.repository.BudgetQueryRepository;
import com.ssafy.completionism.domain.budget.repository.BudgetRepository;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.global.exception.AlreadyExistException;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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


        LocalDate yearMonth = dto.getYearMonth().withDayOfMonth(1);


        Optional<BudgetResponse> alreadyBudget = budgetQueryRepository.findByYearMonthAndCategory(member.getId(), yearMonth, dto.getCategory());

        if (alreadyBudget.isPresent()) {
            throw new AlreadyExistException("409", HttpStatus.CONFLICT, "이미 있는 예산입니다.");
        }

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

    @Override
    public BudgetResponse searchMonthDetail(String loginId, String day) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yearMonth = LocalDate.parse(day, dateTimeFormatter).withDayOfMonth(1);

        Optional<Budget> budgetOptional = budgetQueryRepository.findByYearMonthAndMember(yearMonth, member);

        if (budgetOptional.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 예산을 찾지 못했습니다.");
        }

        return BudgetResponse.toResponse(budgetOptional.get());
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
        Optional<Budget> budgetOptional = budgetQueryRepository.findByYearMonthAndMember(dto.getYearMonth(), member);

        if (budgetOptional.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 예산을 찾을 수 없습니다.");
        }

        budgetOptional.get().updateBudget(dto.getTotalBudget(), dto.getCategory());
    }

    @Override
    public void deleteBudget(String loginId, String yearMonth) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yearMonthLocalDate = LocalDate.parse(yearMonth, dateTimeFormatter);
        Optional<Budget> budgetOptional = budgetQueryRepository.findByYearMonthAndMember(yearMonthLocalDate, member);

        if (budgetOptional.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 예산을 찾지 못했습니다.");
        }

        budgetRepository.delete(budgetOptional.get());
    }
}
