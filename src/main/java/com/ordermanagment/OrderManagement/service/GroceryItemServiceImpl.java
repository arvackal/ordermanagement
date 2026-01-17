package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.CreateGroceryItemRequest;
import com.ordermanagment.OrderManagement.dto.UpdateGroceryRequest;
import com.ordermanagment.OrderManagement.entity.GroceryItem;
import com.ordermanagment.OrderManagement.exceptions.GroceryItemNotFoundException;
import com.ordermanagment.OrderManagement.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroceryItemServiceImpl implements GroceryItemService{

    @Autowired
    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemServiceImpl(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    @Override
    public GroceryItem createGroceryItem(CreateGroceryItemRequest request) {
        GroceryItem groceryItem = new GroceryItem();

        groceryItem.setCategory(request.getCategory());
        groceryItem.setName(request.getName());
        groceryItem.setPrice(request.getPrice());
        groceryItem.setQuantity(request.getQuantity());

        return groceryItemRepository.save(groceryItem);
    }

    @Override
    public GroceryItem getGroceryByItemId(Long id) {
        GroceryItem groceryItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new GroceryItemNotFoundException(id));

        return groceryItem;
    }

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public void deleteGroceryItem(Long id) {
        GroceryItem groceryItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new GroceryItemNotFoundException(id));

        groceryItemRepository.delete(groceryItem);
    }

    @Transactional
    @Override
    public GroceryItem updateGroceryItem(UpdateGroceryRequest request) {
        GroceryItem groceryItem = groceryItemRepository.findById(request.getId())
                .orElseThrow(() -> new GroceryItemNotFoundException(request.getId()));

        groceryItem.setName(request.getName());
        groceryItem.setQuantity(request.getQuantity());
        groceryItem.setCategory(request.getCategory());
        groceryItem.setPrice(request.getPrice());

        return groceryItem;
    }
}
