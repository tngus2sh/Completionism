package com.ssafy.completionism.api.controller.budget;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.budget.request.AddBudgetRequest;
import com.ssafy.completionism.api.controller.budget.request.ModifyBudgetRequest;
import com.ssafy.completionism.api.controller.budget.response.BudgetResponse;
import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.api.service.budget.dto.AddBudgetDto;
import com.ssafy.completionism.api.service.budget.dto.ModifyBudgetDto;
import com.ssafy.completionism.global.exception.AlreadyExistException;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.completionism.global.common.Constants.NOT_FOUND_MEMBER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
@Slf4j
public class BudgetApiController {

    private final BudgetService budgetService;

    @PostMapping
    public ApiResponse<Long> addBudget(
            @Valid @RequestBody AddBudgetRequest request
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();
        log.debug("addBudget :: request = {}", request);
        log.info("addBudget :: request = {}", request);
        AddBudgetDto dto = AddBudgetDto.toDto(request);

        try {
            Long budgetId = budgetService.addBudget(loginId, dto);
            return ApiResponse.ok(budgetId);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        } catch (AlreadyExistException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @GetMapping
    public ApiResponse<List<MonthBudgetResponse>> showMonth() {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            List<MonthBudgetResponse> list = budgetService.searchMonthAll(loginId);
            return ApiResponse.ok(list);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        }
    }

    @GetMapping("/{period}")
    public ApiResponse<List<MonthBudgetResponse>> showMonthForPeriod(
            @PathVariable String period
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        String[] dates = period.split("_");

        String[] date = dates[0].split("-");
        LocalDate start = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        date = dates[1].split("-");
        LocalDate end = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        try {
            List<MonthBudgetResponse> list = budgetService.searchMonth(loginId, start, end);
            return ApiResponse.ok(list);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        }
    }

    @GetMapping("/detail/{yearMonth}")
    public ApiResponse<BudgetResponse> showDetailMonth(
            @PathVariable String yearMonth
    ) {
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            BudgetResponse budgetResponse = budgetService.searchMonthDetail(loginId, yearMonth);
            return ApiResponse.ok(budgetResponse);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, "해당하는 회원을 찾을 수 없습니다.", null);
        } catch (NotFoundException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @PatchMapping
    public ApiResponse<Long> modifyBudget(
            @Valid @RequestBody ModifyBudgetRequest request
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            budgetService.modifyBudget(loginId, ModifyBudgetDto.toDto(request));
            return ApiResponse.ok(null);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        }
    }

    @DeleteMapping("/{yearMonth}")
    public ApiResponse<Long> removeBudget(
            @PathVariable String yearMonth
    ) {
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            budgetService.deleteBudget(loginId, yearMonth);
            return ApiResponse.ok(null);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        } catch (NotFoundException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }
}
