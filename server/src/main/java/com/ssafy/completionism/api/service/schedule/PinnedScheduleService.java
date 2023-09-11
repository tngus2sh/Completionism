package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PinnedScheduleService {
    Long createPinnedSchedule(String loginId, CreatePinnedScheduleDto dto);
}
