package com.assessment.qpassessment.repository;

import com.assessment.qpassessment.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Item, Integer> {
    List<Item> findByQuantityGreaterThan(int i);
}
