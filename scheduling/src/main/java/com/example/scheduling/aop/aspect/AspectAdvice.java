package com.example.scheduling.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
public class AspectAdvice {

    @Before("com.example.scheduling.aop.pointcuts.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // 리턴값 변경은 안된다
    @AfterReturning(value = "com.example.scheduling.aop.pointcuts.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.example.scheduling.aop.pointcuts.Pointcuts.orderAndService()", throwing = "exception")
    public void doThrowing(JoinPoint joinPoint, Exception exception) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), exception);
    }

    @After("com.example.scheduling.aop.pointcuts.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }

}
