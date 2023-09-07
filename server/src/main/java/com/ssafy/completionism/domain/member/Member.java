package com.ssafy.completionism.domain.member;

import com.ssafy.completionism.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(nullable = false)
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @Builder
    public Member(Long id, String loginId, String loginPwd, String name, String phone, String refreshToken, boolean active, List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.name = name;
        this.phone = phone;
        this.refreshToken = refreshToken;
        this.active = active;
        this.roles = roles;
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
