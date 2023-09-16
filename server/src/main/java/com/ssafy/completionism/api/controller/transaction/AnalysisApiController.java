package com.ssafy.completionism.api.controller.transaction;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.transaction.request.FeedBackRequest;
import com.ssafy.completionism.api.controller.transaction.response.FeedBackResponse;
import com.ssafy.completionism.api.service.transaction.AnalysisService;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class AnalysisApiController {

    private final AnalysisService analysisService;

    @PostMapping
    public ApiResponse<FeedBackResponse> getFeedBack(
            @Valid @RequestBody FeedBackRequest request
    ) {
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            FeedBackResponse feedBack = analysisService.getFeedBack(loginId, request.getType());
            return ApiResponse.ok(feedBack);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, "해당하는 정보를 찾을 수 없습니다.", null);
        }

    }
}
