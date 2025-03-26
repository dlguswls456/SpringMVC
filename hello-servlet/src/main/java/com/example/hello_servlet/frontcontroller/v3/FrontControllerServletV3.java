package com.example.hello_servlet.frontcontroller.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.hello_servlet.frontcontroller.ModelView;
import com.example.hello_servlet.frontcontroller.v2.MyView;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet{

    private Map<String, ControllerV3> controllerMap = new HashMap<>();
    
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri =  request.getRequestURI();
        
        ControllerV3 controller = controllerMap.get(requestUri);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        ModelView modelView = controller.process(createMap(request));
        
        String viewName = modelView.getViewName();
        MyView view = viewResolver(viewName);
        view.render(modelView.getModel(), request, response);
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
    
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
