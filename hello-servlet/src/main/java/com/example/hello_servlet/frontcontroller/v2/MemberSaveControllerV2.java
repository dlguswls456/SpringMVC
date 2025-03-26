package com.example.hello_servlet.frontcontroller.v2;

import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberSaveControllerV2 implements ControllerV2{

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        int age = Integer.parseInt(request.getParameter("age"));
        
        Member member = new Member(userName, age);
        memberRepository.save(member);
        
        request.setAttribute("member", member);
        
        return new MyView("/WEB-INF/views/save-result.jsp");
    }
    
    

}
