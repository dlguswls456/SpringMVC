package com.example.scheduling.pointcuts;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;


/**
 * within지시자는 execution 지시자에서 대상 타입 지정만 가져왔다고 생각하면 된다. 
 * within의 경우는 정확히 타입이 맞아야
 * 매치된다
 */
@Slf4j
public class WithinTest {

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
    void withinExact() {
        pointcut.setExpression("within(com.example.scheduling.pointcuts.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(com.example.scheduling.pointcuts.*emberServi*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.example.scheduling..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타켓의 타입에만 적용 가능, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.example.scheduling.pointcuts.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    
    @Test
    @DisplayName("execution 지시자는 가능")
    void withinSuperTypeTrue() {
        pointcut.setExpression("execution(* com.example.scheduling.pointcuts.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
