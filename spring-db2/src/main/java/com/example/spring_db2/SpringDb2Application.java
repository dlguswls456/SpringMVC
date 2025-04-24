package com.example.spring_db2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.spring_db2.config.JdbcTemplateV3Config;


//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "com.example.spring_db2")
public class SpringDb2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringDb2Application.class, args);
    }

//    @Bean
//    @Profile("local")
//    public TestDataInit testDataInit(ItemRepository itemRepository) {
//        return new TestDataInit(itemRepository);
//    }

}
