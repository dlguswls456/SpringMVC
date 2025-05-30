package com.example.spring_db2.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import com.example.spring_db2.repository.ItemRepository;
import com.example.spring_db2.repository.jdbctemplate.JdbcTemplateItemRepositoryV1;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV1;

import lombok.RequiredArgsConstructor;


//@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }

}
