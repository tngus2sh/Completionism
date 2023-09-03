package com.ssafy.completionism.client.api;

import com.ssafy.completionism.client.schedule.service.FutureScheduleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"미래 예상 소비"})
@RequestMapping("/api/transaction/pinned")
public class FutureScheduleApiController {

    private final FutureScheduleService futureScheduleService;

}
