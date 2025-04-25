package com.example.spring_db2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_db2.repository.ItemRepository;
import com.example.spring_db2.repository.jpa.JpaItemRepositoryV2;
import com.example.spring_db2.repository.jpa.SpringDataJpaItemRepository;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV1;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository springDataJpaItemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(springDataJpaItemRepository);
    }

}
