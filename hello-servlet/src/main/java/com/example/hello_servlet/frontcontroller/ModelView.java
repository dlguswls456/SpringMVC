package com.example.hello_servlet.frontcontroller;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();
    
    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
