package com.example.hello_servlet.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;
import com.example.hello_servlet.frontcontroller.v3.ControllerV3;
import com.example.hello_servlet.frontcontroller.v5.MyHandlerAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerV3;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
                                    throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;
        Map<String, String> param = createMap(request);
        
        ModelView modelView = controller.process(param);
        
        return modelView;
    }

    private Map<String, String> createMap(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        
        Map<String, String> map = new HashMap<>();
        for(String key : requestMap.keySet()) {
            for(String value : requestMap.get(key)) {
                map.put(key, value);
            }
        }
        
        return map;
    }
}
