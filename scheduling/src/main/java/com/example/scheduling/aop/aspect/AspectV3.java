package com.example.scheduling.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* com.example.scheduling.aop..*(..))")
    private void allOrder() { // 포인트컷 시그니쳐
    }

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() { // 클래스 명으로 Service를 포함시키는 곳을 필터링하는 포인트 컷
    }

    // aop 패키지 + 서비스 계층인 경우
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랙잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랙잭션 커밋] {}", joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            log.info("[트랙잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
