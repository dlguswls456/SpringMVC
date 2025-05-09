package com.example.scheduling.pointcuts;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;


/**
 * 인자가 주어진 타입의 인스턴스인 조인 포인트로 매칭
 * 기본 문법은 execution의 args 부분과 같다 .
 * args는 부모 타입을 허용한다. 
 * args는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.
 */
@Slf4j
public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException, SecurityException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("helloMethod={}", helloMethod);
        // 출력 결과
        // public java.lang.String
        // com.example.scheduling.pointcuts.MemberServiceImpl.hello(java.lang.String)
    }

    private AspectJExpressionPointcut getPointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        assertThat(getPointcut("args(String)")
                                        .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args(Object)")
                                        .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args()")
                                        .matches(helloMethod, MemberService.class)).isFalse();
        assertThat(getPointcut("args(..)")
                                        .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args(*)")
                                        .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args(String, ..)")
                                        .matches(helloMethod, MemberService.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)) : 메서드의 시그니처로 판단 (정적) -> 실제 메서드의 인자 타입을 확인
     * args(java.io.Serializable) : 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {

        // Args
        assertThat(getPointcut("args(String)")
                .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("args(Object)")
                .matches(helloMethod, MemberService.class)).isTrue();

        // Execution
        assertThat(getPointcut("execution(* *(String))")
                .matches(helloMethod, MemberService.class)).isTrue();
        assertThat(getPointcut("execution(* *(java.io.Serializable))")
                .matches(helloMethod, MemberService.class)).isFalse();
        assertThat(getPointcut("execution(* *(Object))")
                .matches(helloMethod, MemberService.class)).isFalse();
    }

}
