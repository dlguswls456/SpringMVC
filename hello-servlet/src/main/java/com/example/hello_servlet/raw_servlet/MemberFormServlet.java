package com.example.hello_servlet.raw_servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebServlet(name = "memberFormServlet", urlPatterns = "/servlet/members/new-form")
public class MemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
                                    throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();
        StringBuilder sb = new StringBuilder();
        String html = sb.append("<!DOCTYPE html>\n")
                        .append("<html>\n")
                        .append("<head>\n")
                        .append("    <meta charset=\"UTF-8\">\n")
                        .append("    <title>Title</title>\n")
                        .append("</head>\n")
                        .append("<body>\n")
                        .append("<form action=\"/servlet/members/save\" method=\"post\">\n")
                        .append("    username: <input type=\"text\" name=\"userName\" />\n")
                        .append("    age:      <input type=\"text\" name=\"age\" />\n")
                        .append(" <button type=\"submit\">전송</button>\n")
                        .append("</form>\n")
                        .append("</body>\n")
                        .append("</html>\n").toString();
        
        w.write(html);
    }

}
