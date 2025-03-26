package com.example.hello_servlet.frontcontroller.v3;

import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;
import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;


public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> param) {
        String userName = param.get("userName");
        int age = Integer.parseInt(param.get("age"));

        Member member = new Member(userName, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);
        return modelView;
    }

}
