package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;
import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FutureScheduleService {
    Long createFutureSchedule(CreateFutureScheduleRequest request);
    void modifyFutureSchedule(ModifyFutureScheduleRequest request);
}
