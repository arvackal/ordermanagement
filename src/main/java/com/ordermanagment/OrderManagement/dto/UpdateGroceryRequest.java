package com.ordermanagment.OrderManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGroceryRequest {
    @NotNull
    private Long id;

    @NotBlank
    private String category;

    @NotBlank
    private String name;

    @Min(value = 1, message = "Price should be > 0")
    private Double price;

    @Min(value = 1, message = "Quantity should > 0")
    private Long quantity;
}
