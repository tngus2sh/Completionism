package com.ssafy.completionism.api.service.member.impl;

import com.ssafy.completionism.api.controller.member.request.LoginMemberRequest;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.api.service.member.MemberService;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberQueryRepository;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.global.jwt.JwtToken;
import com.ssafy.completionism.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Override
    public JwtToken loginMember(LoginMemberRequest request) {
        String loginId = request.getLoginId();

        Optional<Member> targetUser = memberQueryRepository.getByLoginIdAndActive(loginId, true);
        if(targetUser.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다.");
        }

        // 로그인한 ID, PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, request.getLoginPwd());

        // 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        Member member = targetUser.get();
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, member.getName(), member.getPhone());

        // 데이터베이스에 refreshToken 저장
        memberRepository.updateRefreshToken(loginId, jwtToken.getRefreshToken());

        return jwtToken;
    }
}
