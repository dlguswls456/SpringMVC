package com.example.itemservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.example.itemservice.typeconverter.converter.IpPortToStringConverter;
import com.example.itemservice.typeconverter.converter.StringToIpPortConverter;
import com.example.itemservice.typeconverter.formatter.MyNumberFormatter;
import com.example.itemservice.typeconverter.type.IpPort;


public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService fcs = new DefaultFormattingConversionService();

        // 컨버터 등록
        fcs.addConverter(new StringToIpPortConverter());
        fcs.addConverter(new IpPortToStringConverter());

        // 포메터 등록
        fcs.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = fcs.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        // 포메터 사용
        assertThat(fcs.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(fcs.convert("1,000", Long.class)).isEqualTo(1000L);
    }

}
