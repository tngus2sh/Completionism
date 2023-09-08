package com.ssafy.completionism.api.controller.budget;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.budget.request.AddBudgetRequest;
import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import com.ssafy.completionism.api.service.budget.dto.AddBudgetDto;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
@Slf4j
public class BudgetController {

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

        Long budgetId = budgetService.addBudget(loginId, dto);
        return ApiResponse.ok(budgetId);
    }

    @GetMapping
    public ApiResponse<List<MonthBudgetResponse>> getMonthAll() {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        List<MonthBudgetResponse> list = budgetService.searchMonthAll(loginId);
        return ApiResponse.ok(list);
    }

    @GetMapping("/{period}")
    public ApiResponse<List<MonthBudgetResponse>> getMonth(
            @PathVariable String period
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        String[] dates = period.split("_");

        String[] date = dates[0].split("-");
        LocalDate start = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        date = dates[1].split("-");
        LocalDate end = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        List<MonthBudgetResponse> list = budgetService.searchMonth(loginId, start, end);
        return ApiResponse.ok(list);
    }

}
