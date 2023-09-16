package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.controller.schedule.response.ScheduleResponse;
import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PinnedScheduleService {
    Long createPinnedSchedule(String loginId, CreatePinnedScheduleDto dto);
    List<ScheduleResponse> searchPinnedScheduleAll(String loginId);
    Integer countNextPinnedSchedule(String loginId, String date);
}
