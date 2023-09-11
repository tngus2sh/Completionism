package com.ssafy.completionism.api.controller.schedule;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.schedule.request.CreatePinnedScheduleRequest;
import com.ssafy.completionism.api.service.schedule.PinnedScheduleService;
import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/schedule/pinned")
public class PinnedScheduleApiController {

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
}
