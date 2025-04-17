package com.example.itemservice.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode // equals(), hashCode() 생성, 필드 값이 전부 같다면 동일하다고 판단
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

}
