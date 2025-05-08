package com.example.scheduling.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;


public class Pointcuts {

    @Pointcut("execution(* com.example.scheduling.aop..*(..))")
    public void allOrder() { // 포인트컷 시그니쳐
    }

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() { // 클래스 명으로 Service를 포함시키는 곳을 필터링하는 포인트 컷
    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {
    }

}
