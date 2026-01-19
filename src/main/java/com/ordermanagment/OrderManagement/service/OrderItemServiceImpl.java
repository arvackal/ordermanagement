package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.OrderItemRequest;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.entity.Order;
import com.ordermanagment.OrderManagement.entity.OrderItem;
import com.ordermanagment.OrderManagement.repository.OrderItemRepository;
import com.ordermanagment.OrderManagement.repository.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRespository;

    @Autowired
    private OrderService orderService;

    @Override
    public OrderItem findOrderItemById(Long id) {
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        return List.of();
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        return orderItemRespository.findByOrderId(orderId);
    }
}
