package com.example.itemservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.itemservice.typeconverter.converter.IntegerToStringConverter;
import com.example.itemservice.typeconverter.converter.IpPortToStringConverter;
import com.example.itemservice.typeconverter.converter.StringToIntegerConverter;
import com.example.itemservice.typeconverter.converter.StringToIpPortConverter;
import com.example.itemservice.typeconverter.type.IpPort;


public class ConverterTest {

    @Test
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString() {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(100);
        assertThat(result).isEqualTo("100");
    }

    @Test
    void stringToIpPort() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        IpPort result = converter.convert("127.0.0.1:8080");
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void IpPortToString() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        String result = converter.convert(new IpPort("127.0.0.1", 8080));
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

}
