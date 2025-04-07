package com.example.hello_servlet.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hello_servlet.controller.model.HelloData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * request parameter
 * 
 * - GET - 쿼리 파라미터 
 * /url?userName=hello&age=20
 * 
 * - POST 
 * - html form 
 * 메세지 바디에 쿼리 파라미터 형식으로 전달
 * 
 * - HTTP message body 
 * POST, PUT, PATCH
 * 
 */
@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("userName={}, age={}", userName, age);

        response.getWriter().write("okay");
    }

    /**
     * @RequestParam 사용 - 파라미터 이름으로 바인딩
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("userName") String userName, @RequestParam("age") int age) {
        log.info("userName={}, age={}", userName, age);
        return "ok";
    }

    /**
     * @RequestParam 사용 
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     * 근데 왜 난 오류나지..
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String userName, @RequestParam int age) {
        log.info("userName={}, age={}", userName, age);
        return "ok";
    }

    /**
     * @RequestParam 사용 
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     * 근데 왜 난 오류나지..
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String userName, int age) {
        log.info("userName={}, age={}", userName, age);
        return "ok";
    }
    
    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     * 
     * 파라미터를 Map으로 조회
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requesParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("userName={}, age={}", paramMap.get("userName"), paramMap.get("age"));
        return "ok";
        
    }
    
    /**
    * @ModelAttribute 사용
    * 알아서 데이터 클래스에 값을 넣어준다
    * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨
    */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("userName={}, age={}", helloData.getUserName(), helloData.getAge());
        return "ok";
    }
    
    /**
    * @ModelAttribute 생략 가능
    * String, int 같은 단순 타입 = @RequestParam
    * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
    */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("userName={}, age={}", helloData.getUserName(), helloData.getAge());
        return "ok";
    }
    

}
