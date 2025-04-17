package com.example.itemservice.typeconverter.converter;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String value) {
        log.info("convert source={}", value);
        return Integer.valueOf(value);
    }

}
