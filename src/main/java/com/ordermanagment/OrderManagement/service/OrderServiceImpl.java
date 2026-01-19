package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.CreateOrderRequest;
import com.ordermanagment.OrderManagement.dto.OrderItemRequest;
import com.ordermanagment.OrderManagement.entity.Customer;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.entity.Order;
import com.ordermanagment.OrderManagement.entity.OrderItem;
import com.ordermanagment.OrderManagement.enums.OrderStatus;
import com.ordermanagment.OrderManagement.exceptions.*;
import com.ordermanagment.OrderManagement.repository.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroceryItemService groceryItemService;

    @Autowired
    private OrderRespository orderRespository;

    @Override
    public Order createOrder(CreateOrderRequest orderRequest) {
        Order order = new Order();

        Customer customer = customerService.getCustomerbyId(orderRequest.getCustomerId());

        if(customer == null)
            throw new CustomerNotFoundException(orderRequest.getCustomerId());

        order.setCustomer(customer);

        for(OrderItemRequest orderItemRequest: orderRequest.getOrderItemRequests()){
            Long groceryItemId = orderItemRequest.getGroceryItemId();

            GroceryItem groceryItem = groceryItemService.getGroceryByItemId(groceryItemId);

            if(groceryItem == null)
                throw new GroceryItemNotFoundException(groceryItemId);

            OrderItem orderItem = new OrderItem();
            orderItem.setGroceryItem(groceryItem);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);

        }

        Double totalPrice = 0.0;
        List<OrderItem> orderItems= order.getOrderItems();

        for(OrderItem orderItem: orderItems){
            Double groceryItemPrice = orderItem.getGroceryItem().getPrice();
            int quantity = orderItem.getQuantity();

            totalPrice += groceryItemPrice * quantity;
        }

        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPlacedOn(LocalDate.now());
        orderRespository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {

        Order order = orderRespository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return order;
    }

    @Override
    public List<Order> getOrderByCustomerId(Long customerId) {

        Customer customer = customerService.getCustomerbyId(customerId);

        List<Order> orders = orderRespository.findByCustomerId(customerId);

        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRespository.findAll();
    }

    private Boolean checkCancelPossible(Order order){
        OrderStatus currentStatus = order.getOrderStatus();

        return currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PLACED;
    }

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        Order order = orderRespository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Boolean isPossible = checkCancelPossible(order);

        if(isPossible){
            System.out.println("cancelling");
            updateOrderStatus(id, "CANCELLED");

        }else{
            throw new CancelNotPossibleException("Order is already Shipped/Cancelled/Delivered");
        }
    }

    @Transactional
    @Override
    public Order updateOrderStatus(Long id, String orderStatusString) {
        Order order = orderRespository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));


        if(orderStatusString == null || orderStatusString.trim().isBlank())
            throw new UnknownOrderStatusFoundException(orderStatusString);

        try{
            OrderStatus orderStatus = OrderStatus.valueOf(orderStatusString.toUpperCase());
            order.setOrderStatus(orderStatus);

            return order;
        } catch (IllegalArgumentException e) {
            throw new UnknownOrderStatusFoundException(orderStatusString);
        }

    }
}
