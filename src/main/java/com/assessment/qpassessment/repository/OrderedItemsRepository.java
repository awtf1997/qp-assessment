package com.assessment.qpassessment.repository;

import com.assessment.qpassessment.entity.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItem, Integer> {
    List<OrderedItem> findByOrderId(Integer id);

    void deleteAllByOrderId(Integer id);

    @Query(value = "UPDATE ORDERED_ITEMS " +
            "SET ITEM_QUANTITY = ITEM_QUANTITY + :quantity, ITEM_PRICE = ITEM_PRICE + :price * :quantity " +
            "WHERE ORDER_ID = :orderId AND ITEM_ID = :itemId", nativeQuery = true)
    void updateOrderedItems(Integer orderId, Integer itemId, Integer quantity, Double price);

    @Query(value = "UPDATE ORDERED_ITEMS " +
            "SET ITEM_QUANTITY = ITEM_QUANTITY + :quantity, ITEM_PRICE = ITEM_PRICE + ITEM_PRICE * :quantity " +
            "WHERE ORDER_ID = :orderId AND ITEM_ID = :itemId", nativeQuery = true)
    void updateOrderedItems(Integer orderId, Integer itemId, Integer quantity);

}
