package com.ssafy.completionism.api.service.schedule;

import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;

public interface FutureScheduleService {
    Long createFutureSchedule(CreateFutureScheduleRequest request);
}
