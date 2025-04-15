package com.example.itemservice.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.itemservice.login.domain.member.Member;
import com.example.itemservice.login.domain.member.MemberRepository;
import com.example.itemservice.login.web.session.CustomSessionManager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final CustomSessionManager sessionManager;

//    @GetMapping("/")
    public String cookieHome(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if(memberId == null) {
            return "login/home";
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) {
            return "login/home";
        }

        model.addAttribute("member", loginMember);
        return "login/loginHome";

    }

    @GetMapping("/")
    public String customSessionHome(HttpServletRequest request, Model model) {
        Member member = (Member) sessionManager.getSession(request);
        if(member == null) {
            return "login/home";
        }

        // 로그인
        model.addAttribute("member", member);
        return "login/loginHome";

    }

}
