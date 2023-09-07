package com.ssafy.completionism.api.service.member;

import com.ssafy.completionism.api.service.member.dto.LoginMemberDto;
import com.ssafy.completionism.api.service.member.dto.SignupMemberDto;
import com.ssafy.completionism.global.jwt.JwtToken;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    // 회원가입
    Long signupMember(SignupMemberDto dto);

    // 로그인
    JwtToken loginMember(LoginMemberDto dto);

    // 로그아웃
    void logoutMember(String loginId);
}
