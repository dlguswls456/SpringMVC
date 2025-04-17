package com.example.itemservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import com.example.itemservice.typeconverter.converter.IntegerToStringConverter;
import com.example.itemservice.typeconverter.converter.IpPortToStringConverter;
import com.example.itemservice.typeconverter.converter.StringToIntegerConverter;
import com.example.itemservice.typeconverter.converter.StringToIpPortConverter;
import com.example.itemservice.typeconverter.type.IpPort;


public class ConversionServiceTest {

    @Test
    void conversionService() {
        // 등록
        DefaultConversionService cs = new DefaultConversionService();
        cs.addConverter(new StringToIntegerConverter());
        cs.addConverter(new IntegerToStringConverter());
        cs.addConverter(new StringToIpPortConverter());
        cs.addConverter(new IpPortToStringConverter());

        // 사용
        assertThat(cs.convert("10", Integer.class)).isEqualTo(10);
        assertThat(cs.convert(10, String.class)).isEqualTo("10");

        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        assertThat(cs.convert("127.0.0.1:8080", IpPort.class)).isEqualTo(ipPort);
        assertThat(cs.convert(ipPort, String.class)).isEqualTo("127.0.0.1:8080");
    }

}
