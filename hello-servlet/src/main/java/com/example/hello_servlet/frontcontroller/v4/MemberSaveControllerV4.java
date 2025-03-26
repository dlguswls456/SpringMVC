package com.example.hello_servlet.frontcontroller.v4;

import java.util.Map;

import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;


public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> param, Map<String, Object> model) {
        String userName = param.get("userName");
        int age = Integer.parseInt(param.get("age"));
        
        Member member = new Member(userName, age);
        memberRepository.save(member);
        
        model.put("member", member); // 모델이 파라미터로 전달되기 때문에 모델을 직접 생성하지 않아도 된다.
        
        return "save-result";
    }

}
