package com.example.hello_servlet.frontcontroller.v4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.hello_servlet.frontcontroller.v2.MyView;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet{

    private Map<String, ControllerV4> controllerMap = new HashMap<>();
    
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri =  request.getRequestURI();
        
        ControllerV4 controller = controllerMap.get(requestUri);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> param = createMap(request);
        Map<String, Object> model = new HashMap<>(); //추가
        
        String viewName = controller.process(param, model);
        
        MyView view = viewResolver(viewName);
        view.render(model, request, response);
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
