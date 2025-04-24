package com.example.spring_db2.config;

import org.springframework.context.annotation.Bean;

import com.example.spring_db2.repository.ItemRepository;
import com.example.spring_db2.repository.MemoryItemRepository;
import com.example.spring_db2.service.ItemService;
import com.example.spring_db2.service.ItemServiceV1;


//@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
