package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import com.ssafy.completionism.api.service.schedule.dto.ModifyFutureScheduleDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FutureScheduleService {
    Long createFutureSchedule(String loginId, CreateFutureScheduleDto dto);
    void modifyFutureSchedule(String loginId, ModifyFutureScheduleDto dto);
    void removeFutureSchedule(String loginId, Long id);
}
