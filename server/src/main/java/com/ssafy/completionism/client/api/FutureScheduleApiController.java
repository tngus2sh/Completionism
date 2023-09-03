package com.ssafy.completionism.client.api;

import com.ssafy.completionism.client.api.request.CreateFutureScheduleRequest;
import com.ssafy.completionism.client.Schedule.service.FutureScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
