package com.ssafy.completionism.api.controller.schedule;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.schedule.request.CreatePinnedScheduleRequest;
import com.ssafy.completionism.api.controller.schedule.response.ScheduleResponse;
import com.ssafy.completionism.api.service.schedule.PinnedScheduleService;
import com.ssafy.completionism.api.service.schedule.ScheduleService;
import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import com.ssafy.completionism.global.exception.NoAuthorizationException;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/schedule/pinned")
public class PinnedScheduleApiController {

    private final ScheduleService scheduleService;
    private final PinnedScheduleService pinnedScheduleService;

    @PostMapping
    public ApiResponse<Void> createPinnedSchedule(@RequestBody CreatePinnedScheduleRequest request) {
        log.debug("CreatePinnedScheduleRequest={}", request);

        CreatePinnedScheduleDto dto = CreatePinnedScheduleDto.toDto(request);

        try {
            Long pinnedScheduleId = pinnedScheduleService.createPinnedSchedule(SecurityUtils.getCurrentLoginId(), dto);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removePinnedSchedule(@PathVariable("id") Long id) {
        log.debug("scheduleId={}", id);

        try {
            scheduleService.removeSchedule(SecurityUtils.getCurrentLoginId(), id);
        }
        catch(NoAuthorizationException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        return ApiResponse.ok(null);
    }

    @GetMapping()
    public ApiResponse<List<ScheduleResponse>> searchPinnedScheduleAll() {
        log.debug("searchPinnedScheduleAll");

        try {
            List<ScheduleResponse> response = pinnedScheduleService.searchPinnedScheduleAll(SecurityUtils.getCurrentLoginId());
            return ApiResponse.ok(response);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @GetMapping("/next/{date}")
    public ApiResponse<Integer> countNextPinnedSchedule(@PathVariable("date") String date) {
        log.debug("countNextPinnedSchedule={}", date);

        try {
            Integer total = pinnedScheduleService.countNextPinnedSchedule(SecurityUtils.getCurrentLoginId(), date);
            return ApiResponse.ok(total);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }
}
