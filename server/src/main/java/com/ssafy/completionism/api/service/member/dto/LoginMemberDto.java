package com.ssafy.completionism.api.service.member.dto;

import com.ssafy.completionism.api.controller.member.request.LoginMemberRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginMemberDto {

    private String loginId;
    private String loginPwd;


    @Builder
    public LoginMemberDto(String loginId, String loginPwd) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
    }

    public static LoginMemberDto toDto(LoginMemberRequest request) {
        return LoginMemberDto.builder()
                .loginId(request.getLoginId())
                .loginPwd(request.getLoginPwd())
                .build();
    }
}
