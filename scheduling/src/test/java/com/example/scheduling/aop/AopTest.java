package com.example.scheduling.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.scheduling.aop.aspect.AspectAdvice;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
//@Import(AspectV1.class)
//@Import(AspectV2.class)
//@Import(AspectV3.class)
//@Import(AspectV4.class)
@Import(AspectAdvice.class)
public class AopTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

//    @Test
//    void aopInfo() {
//        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
//        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
//    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

//    @Test
//    void exception() {
//        assertThatThrownBy(() -> orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
//    }

}
