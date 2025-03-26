package com.example.hello_servlet.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;
import com.example.hello_servlet.frontcontroller.v4.ControllerV4;
import com.example.hello_servlet.frontcontroller.v5.MyHandlerAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerV4;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
                                    throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        
        Map<String, String> param = createMap(request);
        Map <String, Object> model = new HashMap<String, Object>();
        
        String viewName = controller.process(param, model);
        
        // ControllerV4는 뷰의 이름을 반환하지만 어댑터는 ModelView를 만들어서 반환
        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);
        
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
