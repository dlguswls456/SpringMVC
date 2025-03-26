package com.example.hello_servlet.frontcontroller.v3;

import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;


public interface ControllerV3 {

    ModelView process(Map<String, String> param);

}
