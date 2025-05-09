package com.example.scheduling.pointcuts;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 * @SpringBootTest(properties = "spring.aop.proxy-target-class=true") -> CGLIB 프록시
 * @SpringBootTest(properties = "spring.aop.proxy-target-class=false") -> JDK 프록시
 */
@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모 타입은 상관없이 사용
        @Around("this(com.example.scheduling.pointcuts.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입은 상관없이 사용
        @Around("target(com.example.scheduling.pointcuts.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // JDK 동적 프록시를 사용하면, 프록시에 인터페이스를 상속받은 객체가 생성되기 때문에 Impl 을 알지 못함
        // -> JDK 프록시를 사용하는 경우 AOP 적용 안된다.
        @Around("this(com.example.scheduling.pointcuts.MemberServiceImpl)")
        public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        // 타겟은 Impl 로 지정되기 때문에 상관 없다.
        @Around("target(com.example.scheduling.pointcuts.MemberServiceImpl)")
        public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
