package com.ordermanagment.OrderManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroceryItemNotFoundException extends RuntimeException{
    public GroceryItemNotFoundException(Long groceryitem_id){
        super("Grocery item not found with id: " + groceryitem_id);
    }
}
