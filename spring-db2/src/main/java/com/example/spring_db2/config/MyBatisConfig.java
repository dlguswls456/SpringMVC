package com.example.spring_db2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_db2.repository.ItemRepository;
import com.example.spring_db2.repository.mybatis.ItemMapper;
import com.example.spring_db2.repository.mybatis.MyBatisItemRepository;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV1;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    @Autowired
    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }

}
