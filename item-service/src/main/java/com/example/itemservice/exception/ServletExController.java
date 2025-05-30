package com.example.itemservice.exception;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/exception")
public class ServletExController {

    @GetMapping("/runtime")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

}
