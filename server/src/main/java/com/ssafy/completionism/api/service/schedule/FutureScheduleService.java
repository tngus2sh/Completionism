package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FutureScheduleService {
    Long createFutureSchedule(String loginId, CreateFutureScheduleDto dto);
    void modifyFutureSchedule(ModifyFutureScheduleRequest request);
    void removeFutureSchedule(Long id);
}
