package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.CreateOrderRequest;
import com.ordermanagment.OrderManagement.entity.Order;
import com.ordermanagment.OrderManagement.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    public Order createOrder(CreateOrderRequest orderRequest);
    public Order getOrderById(Long id);
    public List<Order> getOrderByCustomerId(Long customerId);
    public List<Order> getAllOrders();
    public void cancelOrder(Long id);
    public Order updateOrderStatus(Long id, OrderStatus orderStatus);
}
