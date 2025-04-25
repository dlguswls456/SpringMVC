package com.example.spring_db2.repository;

import static com.example.spring_db2.domain.QItem.item;

import java.util.List;

import org.springframework.util.StringUtils;

import com.example.spring_db2.domain.Item;
import com.example.spring_db2.domain.ItemSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;


/**
 * Querydsl을 사용해서 복잡한 쿼리 문제를 해결
 */
public class ItemQueryRepositoryV2 {

    private final JPAQueryFactory query;

    public ItemQueryRepositoryV2(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Item> findAll(ItemSearchCond cond) {
        return query.select(item)
                                        .from(item)
                                        .where(maxPrice(cond.getMaxPrice()),
                                                                        likeItemName(cond.getItemName()))
                                        .fetch();
    }

    private BooleanExpression likeItemName(String itemName) {
        if(StringUtils.hasText(itemName)) {
            return item.itemName.like("%" + itemName + "%");
        }

        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice) {
        if(maxPrice != null) {
            return item.price.loe(maxPrice);
        }

        return null;
    }

}
