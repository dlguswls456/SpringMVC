package com.example.hello_servlet.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {
    
    /*
     * GET /mapping/users
     */
    @GetMapping
    public String users() {
        return "get users";
    }
    
    /*
     * POST /mapping/users
     */
    @PostMapping
    public String addUser() {
        return "post user";
    }
    
    /**
    * GET /mapping/users/{userId}
    */
    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "get userId = " + userId;
    }
    
    /*
     * PATCH /mapping/users/{userId}
     */
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String userId) {
        return "patch userId = " + userId;
    }
    
    /*
     * DELETE /mapping/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return "delete userId = " + userId;
    }

}
