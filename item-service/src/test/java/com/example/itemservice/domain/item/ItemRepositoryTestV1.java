package com.example.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.example.itemservice.domain.itemV1.ItemV1;
import com.example.itemservice.domain.itemV1.ItemRepositoryV1;


public class ItemRepositoryTestV1 {

    ItemRepositoryV1 itemRepository = new ItemRepositoryV1();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        ItemV1 item = new ItemV1("Item A", 10000, 10);

        // when
        ItemV1 savedItem = itemRepository.save(item);

        // then
        ItemV1 findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        // given
        ItemV1 item1 = new ItemV1("Item A", 10000, 10);
        ItemV1 item2 = new ItemV1("Item B", 10000, 10);

        ArrayList<ItemV1> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        // when
        itemRepository.save(item1);
        itemRepository.save(item2);

        ArrayList<ItemV1> savedList = (ArrayList<ItemV1>) itemRepository.findAll();

        // then
        assertThat(savedList.size()).isEqualTo(2);
        assertThat(savedList).contains(item1, item2);
    }

    @Test
    void findById() {
        // given
        ItemV1 item = new ItemV1("Item A", 10000, 10);

        // when
        ItemV1 savedItem = itemRepository.save(item);
        ItemV1 findItem = itemRepository.findById(item.getId());
        
        // then
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        // given
        ItemV1 originItem = new ItemV1("Item A", 10000, 10);
        itemRepository.save(originItem);
        
        ItemV1 updateItem = new ItemV1("Item B", 20000, 10);

        // when
        itemRepository.update(originItem.getId(), updateItem);
        
        ItemV1 findItem = itemRepository.findById(originItem.getId());

        // then
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
    }

}
