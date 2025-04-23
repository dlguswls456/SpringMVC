package com.example.spring_db2.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

import com.example.spring_db2.domain.Item;
import com.example.spring_db2.domain.ItemSearchCond;
import com.example.spring_db2.domain.ItemUpdateDto;

//@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);

        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = store.get(itemId);

        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        return store.values().stream().filter(item -> {
            if(ObjectUtils.isEmpty(itemName)) {
                return true;
            }

            return item.getItemName().contains(itemName);
        }).filter(item -> {
            if(maxPrice == null) {
                return true;
            }

            return item.getPrice() <= maxPrice;
        }).collect(Collectors.toList());
    }

    public void clearStore() {
        store.clear();
    }

}
