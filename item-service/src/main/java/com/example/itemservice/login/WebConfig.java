package com.example.itemservice.login;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.itemservice.exception.resolver.MyHandlerExceptionResolver;
import com.example.itemservice.exception.resolver.UserHandlerExceptionResolver;
import com.example.itemservice.login.web.argumentresolver.LoginMemberArgumentResolver;
import com.example.itemservice.login.web.filter.LogFilter;
import com.example.itemservice.login.web.filter.LoginCheckFilter;
import com.example.itemservice.login.web.interceptor.LogInterceptor;
import com.example.itemservice.login.web.interceptor.LoginCheckInterceptor;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;


// WebMvcConfigurer -> 인터셉터를 위한 
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
    FilterRegistrationBean<Filter> logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        // doFilter()가 ERROR 디스패치도 받을 수 있어짐!
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return filterRegistrationBean;
    }

//    @Bean
    FilterRegistrationBean<Filter> loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).order(1).addPathPatterns("/**").excludePathPatterns("/css/**",
                                        "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor()).order(2).addPathPatterns("/**").excludePathPatterns("/",
                                        "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error",
                                        "/exception/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

}
