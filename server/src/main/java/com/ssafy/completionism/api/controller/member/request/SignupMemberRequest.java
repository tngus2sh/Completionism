package com.ssafy.completionism.api.controller.member.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SignupMemberRequest {

    @NotBlank
    @Size(max = 15)
    private String loginId;

    @NotBlank
    @Size(min = 8, max = 16)
    private String loginPwd;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;


    @Builder
    public SignupMemberRequest(String loginId, String loginPwd, String name, String phone) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.name = name;
        this.phone = phone;
    }
}
