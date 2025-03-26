package com.example.hello_servlet.frontcontroller.v5;

import java.io.IOException;

import com.example.hello_servlet.frontcontroller.ModelView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface MyHandlerAdapter {

    /*
     * handler는 컨트롤러를 의미
     * 어댑터가 해당 컨트롤러를 처리할 수 있는지 판한하는 메서드
     */
    boolean supports(Object handler);

    /*
     * 어댑터는 실제 컨트롤러를 호출, ModelView를 반환
     * 실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환 해야 함
     * 이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출
     * 이제는 이 어댑터를 통해서 호출!
     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
                                    throws ServletException, IOException;

}
