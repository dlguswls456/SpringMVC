package com.example.spring_db2.repository.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.spring_db2.domain.Item;
import com.example.spring_db2.domain.ItemSearchCond;
import com.example.spring_db2.domain.ItemUpdateDto;


@Mapper
public interface ItemMapper {

    void save(Item item);

    // 파라미터가 1개만 있으면 @Param을 지정하지 않아도 됨
    // 파라미터가 2개 이상이면 @Param으로 이름을 지정해서 파라미터를 구분
    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

}
