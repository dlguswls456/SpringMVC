package com.example.itemservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.example.itemservice.typeconverter.formatter.MyNumberFormatter;


public class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000L); // 타입 주의
    }
    
    @Test
    void print() {
        String result = formatter.print(100000L, Locale.KOREA);
        assertThat(result).isEqualTo("100,000"); // 타입 주의
    }

}
