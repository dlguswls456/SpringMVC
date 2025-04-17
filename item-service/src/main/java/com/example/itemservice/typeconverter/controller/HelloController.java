package com.example.itemservice.typeconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itemservice.typeconverter.type.IpPort;


@RestController
@RequestMapping("/converter")
public class HelloController {

    // http://localhost:8080/ip-port?ipPort=127.0.0.1:8080
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam("ipPort") IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());
        return "ok";
    }

}
