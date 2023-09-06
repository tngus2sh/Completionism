package com.ssafy.completionism.api.controller.member;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.member.request.LoginMemberRequest;
import com.ssafy.completionism.api.exception.NotFoundException;
import com.ssafy.completionism.api.service.member.MemberService;
import com.ssafy.completionism.global.jwt.JwtToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"사용자"})
@RequestMapping("/api/auth")
public class MemberApiController {

    private final MemberService memberService;


    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResponse<JwtToken> loginUser(@ApiParam("login-dto") @RequestBody LoginMemberRequest request) {
        log.debug("LoginMemberRequest={}", request);

        try {
            JwtToken response = memberService.loginMember(request);
            return ApiResponse.ok(response);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        catch(BadCredentialsException e) {
            return ApiResponse.of(1, HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호를 잘못 입력하셨습니다.", null);
        }
    }
}
