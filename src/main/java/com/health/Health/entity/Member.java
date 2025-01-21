package com.health.Health.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity // 이 클래스가 DB 테이블과 매핑됨을 나타냄
@Table(name = "Member")
public class Member implements UserDetails { // UserDetails 인터페이스 구현

    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_increment 설정
    private Long id;

    @Column(nullable = false) // null 허용 안함
    private String name;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;


    @Column(nullable = false)
    private String password;

    // UserDetails 인터페이스에서 요구하는 메서드들

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 반환 (예: ROLE_USER 권한)
        return List.of(() -> "ROLE_USER"); // 기본적으로 "ROLE_USER" 권한 부여
    }

    @Override
    public String getUsername() {
        return phoneNumber; // 전화번호를 사용자 이름으로 설정
    }

    @Override
    public String getPassword() {
        return password; // 비밀번호 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (여기서는 만료되지 않도록 설정)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (여기서는 잠금되지 않도록 설정)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 (여기서는 만료되지 않도록 설정)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 (여기서는 활성화되도록 설정)
    }
}
