package com.ssafy.completionism.domain.member;

import com.ssafy.completionism.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
public class Member extends TimeBaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(name = "login_pwd", nullable = false)
    private String loginPwd;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 13)
    private String phone;

    @Column(name = "access_token")
    private String accessToken;

    @Column(nullable = false)
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    public Member() {}

    @Builder
    public Member(Long id, String loginId, String loginPwd, String name, String phone, String accessToken, boolean active, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.name = name;
        this.phone = phone;
        this.accessToken = accessToken;
        this.active = active;
        this.roles = roles;
    }

    public static Member toMember(String loginId, String loginPwd, String name, String phone, boolean active, String role) {
        return Member.builder()
                .loginId(loginId)
                .loginPwd(loginPwd)
                .name(name)
                .phone(phone)
                .active(active)
                .roles(Collections.singletonList(role))
                .build();
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.loginPwd;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
