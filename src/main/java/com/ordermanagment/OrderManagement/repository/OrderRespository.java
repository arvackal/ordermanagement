package com.ordermanagment.OrderManagement.repository;

import com.ordermanagment.OrderManagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRespository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long customerId);
}
