package com.example.itemservice.login.web.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import com.example.itemservice.login.web.session.SessionConst;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = new String[] { "/", "members/add", "/login", "/logout", "/css/*" };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                    throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestUri);

            if(isLoginCheckPath(requestUri)) {
                log.info("인증 체크 로직 실행 {}", requestUri);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestUri);

                    // 로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestUri);
                    return; // 필터를 더는 진행하지 않는다. 이후 필터는 물론 서블릿, 컨트롤러가 더는 호출되지 않는다. 앞서 redirect를 사용했기 때문에
                            // redirect가 응답으로 적용되고 요청이 끝난다.
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestUri);
        }
    }

    public boolean isLoginCheckPath(String requestUri) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestUri);
    }

}
