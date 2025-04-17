package com.example.itemservice.typeconverter.converter;

import java.util.StringTokenizer;

import org.springframework.core.convert.converter.Converter;

import com.example.itemservice.typeconverter.type.IpPort;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert(String source) {
        log.info("convert source={}", source);
        // "127.0.0.1:8080" -> IpPort 객체
        StringTokenizer st = new StringTokenizer(source, ":");
        String ip = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
        return new IpPort(ip, port);
    }

}
