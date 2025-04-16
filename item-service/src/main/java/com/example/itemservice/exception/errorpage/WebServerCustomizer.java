package com.example.itemservice.exception.errorpage;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;


/**
 * 서블릿 오류 페이지 등록
 */
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 지정된 path url로 경로 이동
        ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/exception/error-page/404");
        ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/error-page/500");
        ErrorPage pageEx = new ErrorPage(RuntimeException.class, "/exception/error-page/500");

        factory.addErrorPages(page404, page500, pageEx);
    }

}
