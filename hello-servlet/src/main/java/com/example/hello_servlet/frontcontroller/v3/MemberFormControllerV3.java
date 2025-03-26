package com.example.hello_servlet.frontcontroller.v3;

import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;


public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> param) {
        return new ModelView("new-form");
    }

}
