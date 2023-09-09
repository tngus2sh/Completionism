package com.ssafy.completionism.api.controller.member.request;

import com.ssafy.completionism.api.service.member.dto.LoginMemberDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginMemberRequest {

    @NotBlank
    @Size(max = 15)
    private String loginId;

    @NotBlank
    @Size(min = 8, max = 16)
    private String loginPwd;


    @Builder
    public LoginMemberRequest(String loginId, String loginPwd) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
    }
}
