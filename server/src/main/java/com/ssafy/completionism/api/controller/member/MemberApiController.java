package com.ssafy.completionism.api.controller.member;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.member.request.LoginMemberRequest;
import com.ssafy.completionism.api.controller.member.request.SignupMemberRequest;
import com.ssafy.completionism.api.service.member.dto.LoginMemberDto;
import com.ssafy.completionism.api.service.member.dto.SignupMemberDto;
import com.ssafy.completionism.global.exception.AlreadyExistException;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.api.service.member.MemberService;
import com.ssafy.completionism.global.jwt.JwtToken;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ApiResponse<Void> signupMember(@RequestBody SignupMemberRequest request) {
        log.debug("SignupMemberRequest={}", request);

        SignupMemberDto dto = SignupMemberDto.toDto(request);

        try {
            Long memberId = memberService.signupMember(dto);
            return ApiResponse.ok(null);
        }
        catch(AlreadyExistException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @PostMapping("/login")
    public ApiResponse<JwtToken> loginMember(@RequestBody LoginMemberRequest request) {
        log.debug("LoginMemberRequest={}", request);

        LoginMemberDto dto = LoginMemberDto.toDto(request);

        try {
            JwtToken response = memberService.loginMember(dto);
            return ApiResponse.ok(response);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
        catch(BadCredentialsException e) {
            return ApiResponse.of(1, HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호를 잘못 입력하셨습니다.", null);
        }
    }

    @GetMapping("/logout")
    public ApiResponse<Void> loginMember() {
        String loginId = SecurityUtils.getCurrentLoginId();

        log.debug("logoutMember={}", loginId);

        try {
            memberService.logoutMember(loginId);
            return ApiResponse.ok(null);
        }
        catch(NotFoundException e) {
            return ApiResponse.of(1, e.getHttpStatus(), e.getResultMessage(), null);
        }
    }
}
