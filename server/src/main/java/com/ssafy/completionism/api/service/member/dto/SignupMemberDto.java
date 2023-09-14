package com.ssafy.completionism.api.service.member.dto;


import com.ssafy.completionism.api.controller.member.request.SignupMemberRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupMemberDto {

    private String loginId;
    private String loginPwd;
    private String name;
    private String phone;


    @Builder
    public SignupMemberDto(String loginId, String loginPwd, String name, String phone) {
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.name = name;
        this.phone = phone;
    }

    public static SignupMemberDto toDto(SignupMemberRequest request) {
        return SignupMemberDto.builder()
                .loginId(request.getLoginId())
                .loginPwd(request.getLoginPwd())
                .name(request.getName())
                .phone(request.getPhone())
                .build();
    }
}
