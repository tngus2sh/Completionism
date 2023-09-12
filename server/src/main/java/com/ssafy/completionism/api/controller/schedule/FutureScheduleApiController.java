package com.ssafy.completionism.api.controller.schedule;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;
import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import com.ssafy.completionism.api.controller.schedule.response.FutureScheduleResponse;
import com.ssafy.completionism.api.service.schedule.ScheduleService;
import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import com.ssafy.completionism.api.service.schedule.dto.ModifyFutureScheduleDto;
import com.ssafy.completionism.global.exception.NoAuthorizationException;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.api.service.schedule.FutureScheduleService;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/schedule/future")
public class FutureScheduleApiController {

    private final ScheduleService scheduleService;
    private final FutureScheduleService futureScheduleService;

    @PostMapping
    public ApiResponse<Void> createFutureSchedule(@RequestBody CreateFutureScheduleRequest request) {
        log.debug("CreateFutureScheduleRequest={}", request);

        CreateFutureScheduleDto dto = CreateFutureScheduleDto.toDto(request);

        try {
            Long futureScheduleId = futureScheduleService.createFutureSchedule(SecurityUtils.getCurrentLoginId(), dto);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @PatchMapping
    public ApiResponse<Void> modifyFutureSchedule(@RequestBody ModifyFutureScheduleRequest request) {
        log.debug("ModifyFutureScheduleRequest={}", request);

        ModifyFutureScheduleDto dto = ModifyFutureScheduleDto.toDto(request);

        try {
            futureScheduleService.modifyFutureSchedule(SecurityUtils.getCurrentLoginId(), dto);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeFutureSchedule(@PathVariable(value = "id") Long id) {
        log.debug("scheduleId={}", id);

        try {
            scheduleService.removeSchedule(SecurityUtils.getCurrentLoginId(), id);
        }
        catch(NoAuthorizationException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<FutureScheduleResponse>> searchFutureScheduleAll() {
        log.debug("searchFutureScheduleAll");

        try {
            List<FutureScheduleResponse> response = futureScheduleService.searchFutureScheduleAll(SecurityUtils.getCurrentLoginId());
            return ApiResponse.ok(response);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<FutureScheduleResponse> searchFutureSchedule(@PathVariable("id") Long id) {
        log.debug("searchFutureSchedule");

        try {
            FutureScheduleResponse response = futureScheduleService.searchFutureSchedule(SecurityUtils.getCurrentLoginId(), id);
            return ApiResponse.ok(response);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        catch(NoAuthorizationException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @GetMapping("/daily/{date}")
    public ApiResponse<Integer> countDailyFutureSchedule(@PathVariable("date") String date) {
        log.debug("countDailyFutureSchedule={}", date);

        try {
            Integer total = futureScheduleService.countDailyFutureSchedule(SecurityUtils.getCurrentLoginId(), date);
            return ApiResponse.ok(total);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }
}
