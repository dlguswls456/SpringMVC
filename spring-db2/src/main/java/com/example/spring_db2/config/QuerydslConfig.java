package com.example.spring_db2.config;

import org.springframework.context.annotation.Bean;

import com.example.spring_db2.repository.ItemRepository;
import com.example.spring_db2.repository.jpa.JpaItemRepositoryV3;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV1;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


//@Configuration
@RequiredArgsConstructor
public class QuerydslConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }

}
