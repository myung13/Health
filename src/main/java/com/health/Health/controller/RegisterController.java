package com.health.Health.controller;

import com.health.Health.entity.Member;
import com.health.Health.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegisterPage() {
        return "register"; // 회원가입 페이지 반환
    }

    @PostMapping
    public String register(@RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam String password,
                           Model model) {
        // 전달된 phoneNumber 값 확인
        System.out.println("입력된 전화번호: " + phoneNumber);  // 여기서 phoneNumber 값 확인

        // 전화번호 중복 체크
        if (memberRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            model.addAttribute("error", "이미 사용 중인 전화번호입니다.");
            return "register";
        }
        // Member 객체 생성 및 저장
        Member member = new Member();
        member.setName(name);
        member.setPhoneNumber(phoneNumber); // 폰 넘버 설정
        member.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화
        memberRepository.save(member);

        // 콘솔에 저장된 회원 정보 출력
        System.out.println("회원가입 완료: " + member); // 이 부분을 추가하여 콘솔 로그에서 확인

        return "redirect:/login"; // 회원가입 완료 후 로그인 페이지로 이동
    }
}
