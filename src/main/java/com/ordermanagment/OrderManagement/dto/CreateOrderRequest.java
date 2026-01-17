package com.ordermanagment.OrderManagement.dto;

import com.ordermanagment.OrderManagement.entity.Customer;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.entity.OrderItem;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NonNull
    private Long customerId;

    @Min(1)
    private List<OrderItemRequest> orderItemRequests;
}
