package com.ordermanagment.OrderManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "groceryItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    private List<OrderItem> orderItems = new ArrayList<>();

}
