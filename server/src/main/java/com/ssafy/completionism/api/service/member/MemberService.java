package com.ssafy.completionism.api.service.member;

import com.ssafy.completionism.api.controller.member.request.LoginMemberRequest;
import com.ssafy.completionism.global.jwt.JwtToken;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    // 로그인
    JwtToken loginMember(LoginMemberRequest request);
}
