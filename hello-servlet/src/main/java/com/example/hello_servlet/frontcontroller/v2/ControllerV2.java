package com.example.hello_servlet.frontcontroller.v2;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerV2 {
    MyView process(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException; // 컨트롤러가 뷰를 반환한다.
}
