package com.example.itemservice.login.web.filter;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Log filter doFilter");
        log.info("Dispatcher Type = {}", request.getDispatcherType());
        
        // down casting
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        
        String uuid = UUID.randomUUID().toString(); // 요청 구분
        try {
            log.info("REQUEST [{}][{}]", uuid, requestUri);
            chain.doFilter(request, response); // 다음 필터 호출
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}]", uuid, requestUri);
        }
    }

    @Override
    public void destroy() {
        log.info("Log filter destroy");
    }

}