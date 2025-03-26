package com.example.hello_servlet.frontcontroller.v4;

import java.util.Map;


public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> param, Map<String, Object> model) {
        return "new-form";
    }

}
