package com.health.Health.service;

import com.health.Health.entity.Member;
import com.health.Health.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Member member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("전화번호를 찾을 수 없습니다."));

        return User.builder()
                .username(member.getPhoneNumber()) // 전화번호를 사용자 이름으로 설정
                .password(member.getPassword()) // 암호화된 비밀번호
                .roles("USER") // 필요한 권한을 설정
                .build();
    }
    // 회원가입 기능 추가
    public void registerMember(Member member) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        memberRepository.save(member); // 회원 정보 저장
    }



}
