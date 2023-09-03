package com.ssafy.completionism.client.Schedule.service.impl;

import com.ssafy.completionism.client.Schedule.repository.ScheduleRepository;
import com.ssafy.completionism.client.Schedule.service.FutureScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FutureScheduleServiceImpl implements FutureScheduleService {

    private final ScheduleRepository scheduleRepository;

}
