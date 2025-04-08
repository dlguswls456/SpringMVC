package com.example.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("Item A", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        // given
        Item item1 = new Item("Item A", 10000, 10);
        Item item2 = new Item("Item B", 10000, 10);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        // when
        itemRepository.save(item1);
        itemRepository.save(item2);

        ArrayList<Item> savedList = (ArrayList<Item>) itemRepository.findAll();

        // then
        assertThat(savedList.size()).isEqualTo(2);
        assertThat(savedList).contains(item1, item2);
    }

    @Test
    void findById() {
        // given
        Item item = new Item("Item A", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());
        
        // then
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        // given
        Item originItem = new Item("Item A", 10000, 10);
        itemRepository.save(originItem);
        
        Item updateItem = new Item("Item B", 20000, 10);

        // when
        itemRepository.update(originItem.getId(), updateItem);
        
        Item findItem = itemRepository.findById(originItem.getId());

        // then
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
    }

}
