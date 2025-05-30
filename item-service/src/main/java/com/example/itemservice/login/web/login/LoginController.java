package com.example.itemservice.login.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.itemservice.login.domain.login.LoginForm;
import com.example.itemservice.login.domain.member.Member;
import com.example.itemservice.login.web.session.CustomSessionManager;
import com.example.itemservice.login.web.session.SessionConst;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final CustomSessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String cookieLogin(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                                    HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if(member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리 - 쿠키 생성
        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(member.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

//    @PostMapping("/login")
    public String customSessionLogin(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                                    HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if(member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리 - 세션 생성, 회원 데이터 보관
        sessionManager.createSession(member, response);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String sessionLogin(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                                    HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if(member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환
        // 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:/";
    }
    
    @PostMapping("/login")
    public String filterLogin(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                                    @RequestParam(name="redirectURL", defaultValue = "/") String redirectUrl, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if(member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환
        // 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:" + redirectUrl;
    }

//    @PostMapping("/logout")
    public String cookieLogout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String customSessionLogout(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String sessionLogout(HttpServletRequest request) {
        // 세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // 해당 쿠키는 즉시 종료
        response.addCookie(cookie);
    }

}
