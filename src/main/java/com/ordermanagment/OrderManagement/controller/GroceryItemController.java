package com.ordermanagment.OrderManagement.controller;

import com.ordermanagment.OrderManagement.dto.CreateGroceryItemRequest;
import com.ordermanagment.OrderManagement.dto.UpdateGroceryItemRequest;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.service.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groceryitems")
public class GroceryItemController {

    @Autowired
    private GroceryItemService groceryItemService;

    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems(){
        return ResponseEntity.ok(groceryItemService.getAllGroceryItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id){
        return ResponseEntity.ok(groceryItemService.getGroceryByItemId(id));
    }

    @PostMapping
    public ResponseEntity<GroceryItem> createGroceryItem(@RequestBody CreateGroceryItemRequest request){
        GroceryItem groceryItem = groceryItemService.createGroceryItem(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(groceryItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
            @PathVariable Long id,
            @RequestBody UpdateGroceryItemRequest request){
        GroceryItem groceryItem = groceryItemService.updateGroceryItem(id, request);

        return ResponseEntity.ok(groceryItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id){
        groceryItemService.deleteGroceryItem(id);

        return ResponseEntity.noContent().build();
    }
}
