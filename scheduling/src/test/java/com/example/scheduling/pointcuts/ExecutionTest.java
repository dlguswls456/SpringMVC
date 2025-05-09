package com.example.scheduling.pointcuts;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
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

    @Test
    void exactMatch() {
        // execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
        pointcut.setExpression("execution(public String com.example.scheduling.pointcuts.MemberServiceImpl.hello(java.lang.String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        // 반환타입과 메서드이름(파라미터)
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드 이름으로 매치
    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 패키지명으로 매치
    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatchFalse() {
        pointcut.setExpression("execution(* com.example.scheduling.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatchSubPackage() {
        pointcut.setExpression("execution(* com.example.scheduling..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 대상 타입으로 매치
    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매칭 -> 부모타입으로도 가능
    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 자식에만 있는 메서드는 매칭이 안된다.
    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터 타입으로 매치
    // String 타입의 파라미터 허용
    @Test
    void argsMatch() {
        pointcut.setExpression("execution (* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터를 비워두면 실제로 파라미터가 없어야 매치된다.
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution (* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입
    @Test
    void argsMatchExactOnlyOne() {
        pointcut.setExpression("execution (* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 모든 갯수의 파라미터 허용, 모든 타입 허용
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution (* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 시작은 String, 이후 모든 갯수의 파라미터 허용, 모든 타입 허용
    @Test
    void argsMatchStartStringAndAll() {
        pointcut.setExpression("execution (* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 시작은 String, 이후 단 하나의 타입을 허용
    // 2개가 들어와야하니까 False 가 나옴
    @Test
    void argsMatchStartStringAndStar() {
        pointcut.setExpression("execution (* *(String, *))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

}
