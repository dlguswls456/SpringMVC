package com.example.hello_servlet.raw_servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet{
    
    private MemberRepository memberRepository = MemberRepository.getInstance();
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        int age = Integer.parseInt(request.getParameter("age"));
        
        Member member = new Member(userName, age);
        memberRepository.save(member);
        
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter w = response.getWriter();
        
        StringBuilder sb = new StringBuilder();
        String html = sb.append("<!DOCTYPE html>\n")
                        .append("<html>\n")
                        .append("<body>\n")
                        .append("성공\n")
                        .append("<ul>")
                        .append("    <li>id=" + member.getId() + "</li>\n")
                        .append("    <li>username=" + member.getName() + "</li>\n")
                        .append(" <li>age=" + member.getAge() +  "</li>\n")
                        .append("</ul>\n")
                        .append("</body>\n")
                        .append("</html>").toString();
        w.write(html);
    }

}
