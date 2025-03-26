package com.example.hello_servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class HelloServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloServletApplication.class, args);
    }

}
