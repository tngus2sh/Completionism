package com.ssafy.completionism.api.controller.schedule;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;
import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import com.ssafy.completionism.api.exception.NotFoundException;
import com.ssafy.completionism.api.service.schedule.FutureScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"미래 예상 소비"})
@RequestMapping("/api/transaction/future")
public class FutureScheduleApiController {

    private final FutureScheduleService futureScheduleService;

    @ApiOperation(value = "미래 예상 소비 등록")
    @PostMapping
    public ApiResponse<Void> createFutureSchedule(@ApiParam(value = "future-schedule-dto") @RequestBody CreateFutureScheduleRequest request) {
        log.debug("CreateFutureScheduleRequest={}", request);

        try {
            Long futureScheduleId = futureScheduleService.createFutureSchedule(request);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @ApiOperation(value = "미래 예상 소비 수정")
    @PatchMapping
    public ApiResponse<Void> modifyFutureSchedule(@ApiParam(value = "future-schedule-dto") @RequestBody ModifyFutureScheduleRequest request) {
        log.debug("ModifyFutureScheduleRequest={}", request);

        try {
            futureScheduleService.modifyFutureSchedule(request);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @ApiOperation(value = "미래 예상 소비 삭제")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeFutureSchedule(@ApiParam(value = "schedule-id") @PathVariable(value = "id") Long id) {
        log.debug("scheduleId={}", id);

        futureScheduleService.removeFutureSchedule(id);

        return ApiResponse.ok(null);
    }
}
