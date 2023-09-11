package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.controller.schedule.response.ScheduleResponse;
import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import com.ssafy.completionism.api.service.schedule.dto.ModifyFutureScheduleDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FutureScheduleService {
    Long createFutureSchedule(String loginId, CreateFutureScheduleDto dto);
    void modifyFutureSchedule(String loginId, ModifyFutureScheduleDto dto);
    List<ScheduleResponse> searchFutureScheduleAll(String loginId);
    ScheduleResponse searchFutureSchedule(String loginId, Long id);
}
