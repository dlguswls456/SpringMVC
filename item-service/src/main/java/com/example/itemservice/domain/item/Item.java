package com.example.itemservice.domain.item;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 사실 실무에서는 그룹으로 나눠서 하기 보다는
 * 각 용도에 맞는 데이터 클래스를 만들어서 각각 Validation을 적용한다
 */
@Data
public class Item {

    @NotNull(groups = UpdateCheck.class) //수정시에만 적용
    private Long id;

//    @NotBlank
    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;
    
//    @NotNull
//    @Range(min = 1000, max = 1000000)
    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;
    
//    @NotNull
//    @Max(9999)
    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class) // 등록시에만 적용
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
