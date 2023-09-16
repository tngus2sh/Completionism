package com.ssafy.completionism.api.service.member.impl;

import com.ssafy.completionism.api.service.member.dto.LoginMemberDto;
import com.ssafy.completionism.api.service.member.dto.SignupMemberDto;
import com.ssafy.completionism.global.exception.AlreadyExistException;
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


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public Long signupMember(SignupMemberDto dto) {
        if(memberRepository.findByLoginId(dto.getLoginId()).isPresent()) {
            throw new AlreadyExistException("409", HttpStatus.CONFLICT, "동일한 ID가 존재합니다.");
        }
        else if(memberRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new AlreadyExistException("409", HttpStatus.CONFLICT, "동일한 핸드폰 번호가 존재합니다.");
        }

        Member member = Member.toMember(dto.getLoginId(), passwordEncoder.encode(dto.getLoginPwd()), dto.getName(), dto.getPhone(), true, "USER");
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    // 로그인
    @Override
    public JwtToken loginMember(LoginMemberDto dto) {
        String loginId = dto.getLoginId();

        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."));

        // 로그인한 ID, PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, dto.getLoginPwd());

        // 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, member.getName(), member.getPhone());

        // 데이터베이스에 accessToken 저장
        member.updateAccessToken(jwtToken.getAccessToken());

//         // 데이터베이스에 refreshToken 저장
//        memberRepository.updateRefreshToken(loginId, jwtToken.getRefreshToken());

        return jwtToken;
    }

    // 로그아웃
    @Override
    public void logoutMember(String loginId) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."));

        member.updateAccessToken("");
    }
}
