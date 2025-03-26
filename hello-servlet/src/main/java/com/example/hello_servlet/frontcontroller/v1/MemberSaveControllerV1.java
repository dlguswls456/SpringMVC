package com.example.hello_servlet.frontcontroller.v1;

import java.io.IOException;

import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberSaveControllerV1 implements ControllerV1{

    private MemberRepository memberRepository = MemberRepository.getInstance();
    
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        int age = Integer.parseInt(request.getParameter("age"));
        
        Member member = new Member(userName, age);
        memberRepository.save(member);
        
        request.setAttribute("member", member);
        
        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

}
