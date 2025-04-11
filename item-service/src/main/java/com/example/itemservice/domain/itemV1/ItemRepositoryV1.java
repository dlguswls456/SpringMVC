package com.example.itemservice.domain.itemV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public class ItemRepositoryV1 {

    private static final Map<Long, ItemV1> store = new HashMap<>();
    private static long sequence = 0L;

    public ItemV1 save(ItemV1 item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public ItemV1 findById(Long id) {
        return store.get(id);
    }

    public List<ItemV1> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemV1 updateParam) {
        ItemV1 item = findById(itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
