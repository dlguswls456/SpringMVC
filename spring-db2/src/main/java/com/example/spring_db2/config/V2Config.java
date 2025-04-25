package com.example.spring_db2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_db2.repository.ItemQueryRepositoryV2;
import com.example.spring_db2.repository.ItemRepositoryV2;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV2;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2; // SpringDataJPA

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepository());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepository() {
        return new ItemQueryRepositoryV2(em);
    }

}
