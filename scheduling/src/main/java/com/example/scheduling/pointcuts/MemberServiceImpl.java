package com.example.scheduling.pointcuts;

import org.springframework.stereotype.Component;


@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop(value = "test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }

}
