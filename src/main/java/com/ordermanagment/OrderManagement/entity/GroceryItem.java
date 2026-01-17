package com.ordermanagment.OrderManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GroceryItem {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private Double price;
    private Long quantity;
}
