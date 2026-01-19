package com.ordermanagment.OrderManagement.service;




import com.ordermanagment.OrderManagement.dto.CreateGroceryItemRequest;
import com.ordermanagment.OrderManagement.dto.UpdateGroceryItemRequest;
import com.ordermanagment.OrderManagement.entity.GroceryItem;

import java.util.List;


public interface GroceryItemService {
    public GroceryItem createGroceryItem(CreateGroceryItemRequest createGroceryItemRequest);
    public GroceryItem getGroceryByItemId(Long id);
    public List<GroceryItem> getAllGroceryItems();
    public void deleteGroceryItem(Long id);
    public GroceryItem updateGroceryItem(Long id, UpdateGroceryItemRequest updateGroceryRequest);
}
