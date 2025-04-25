package com.example.spring_db2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_db2.domain.Item;


public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {

}
