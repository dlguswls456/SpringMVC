package com.example.hello_servlet.raw_servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebServlet(name = "hello", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        log.info("HelloServlet.service");
        log.info("request: {}", request);
        log.info("response: {}", response);
        
        String username = request.getParameter("username");
        log.info("username: {}", username);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("username : " + username);
    }

}
