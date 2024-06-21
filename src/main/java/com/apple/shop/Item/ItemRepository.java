package com.apple.shop.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

//    @Query(value = "select * from item where id = ?1",nativeQuery = true)
//    List<Item> sendsql1(Long num); //sendsql1(3)  = id가 3인상품

}
