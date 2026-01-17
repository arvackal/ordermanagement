package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.CreateOrderRequest;
import com.ordermanagment.OrderManagement.dto.OrderItemRequest;
import com.ordermanagment.OrderManagement.entity.Customer;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.entity.Order;
import com.ordermanagment.OrderManagement.entity.OrderItem;
import com.ordermanagment.OrderManagement.enums.OrderStatus;
import com.ordermanagment.OrderManagement.exceptions.CancelNotPossibleException;
import com.ordermanagment.OrderManagement.exceptions.CustomerNotFoundException;
import com.ordermanagment.OrderManagement.exceptions.GroceryItemNotFoundException;
import com.ordermanagment.OrderManagement.exceptions.OrderNotFoundException;
import com.ordermanagment.OrderManagement.repository.OrderRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final CustomerService customerService;
    private final GroceryItemService groceryItemService;
    private final OrderRespository orderRespository;

    public OrderServiceImpl(CustomerService customerService, OrderItemService orderItemService,
                            GroceryItemService groceryItemService, OrderRespository orderRespository) {
        this.customerService = customerService;
        this.groceryItemService = groceryItemService;
        this.orderRespository = orderRespository;
    }

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

        if(currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PLACED)
            return true;

        return false;
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRespository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Boolean isPossible = checkCancelPossible(order);

        if(isPossible){
            updateOrderStatus(id, order.getOrderStatus());
        }else{
            throw new CancelNotPossibleException("Order is already Shipped/Cancelled/Delivered");
        }
    }

    @Transactional
    @Override
    public Order updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRespository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setOrderStatus(orderStatus);

        return order;
    }
}
