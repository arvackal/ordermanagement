package com.ordermanagment.OrderManagement.service;


import com.ordermanagment.OrderManagement.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    public OrderItem findOrderItemById(Long id);
    public List<OrderItem> findAll();
    public List<OrderItem> findByOrderId(Long orderId);
}
