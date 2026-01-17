package com.ordermanagment.OrderManagement.repository;

import com.ordermanagment.OrderManagement.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
}
