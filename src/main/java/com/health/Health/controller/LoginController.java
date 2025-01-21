package com.health.Health.controller;


import com.health.Health.entity.Member;
import com.health.Health.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String login() {
        return "login"; // 로그인 페이지로 이동
    }
    // /login 경로에서도 로그인 페이지를 보여줌
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 로그인 페이지로 이동
    }

    @GetMapping("/exercise-log")
    public String showExerciseLogForm(){
        return "exercise-log";
    }

    @GetMapping("/welcome")
    public String welcome(Model model){
        // SecurityContext에서 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Member) {
            Member member = (Member) authentication.getPrincipal();
            model.addAttribute("welcomeMessage", "환영합니다, " + member.getName() + "님!");
        } else {
            model.addAttribute("welcomeMessage", "환영합니다, 회원님!");
        }

        return "welcome"; // 로그인 성공 후 보여줄 페이지
    }

    @PostMapping("/login")
    public String login(@RequestParam String phoneNumber, @RequestParam String password, Model model) {
        // Optional<Member>로 반환되는 값을 처리
        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(phoneNumber);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 비밀번호가 일치하는지 확인
            if (passwordEncoder.matches(password, member.getPassword())) {
                // 인증 성공 시 SecurityContext에 저장
                Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 로그인 성공 후 welcome 페이지로 리다이렉트
                return "redirect:/welcome";
            } else {
                model.addAttribute("error", "잘못된 전화번호 또는 비밀번호입니다.");
            }
        } else {
            model.addAttribute("error", "잘못된 전화번호 또는 비밀번호입니다.");
        }

        return "login"; // 로그인 실패 시 로그인 페이지로 돌아가기
    }

}
