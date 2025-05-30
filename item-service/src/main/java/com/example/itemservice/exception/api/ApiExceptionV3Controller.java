package com.example.itemservice.exception.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itemservice.exception.api.ApiExceptionController.MemberDto;
import com.example.itemservice.exception.exception.UserException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/exception")
public class ApiExceptionV3Controller {

    @GetMapping("/api3/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello " + id);
    }

}
