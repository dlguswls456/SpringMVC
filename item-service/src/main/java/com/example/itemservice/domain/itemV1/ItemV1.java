package com.example.itemservice.domain.itemV1;

import lombok.Data;

@Data
public class ItemV1 {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemV1() {

    }

    public ItemV1(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
