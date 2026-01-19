package com.ordermanagment.OrderManagement.service;

import com.ordermanagment.OrderManagement.dto.CreateCustomerRequest;
import com.ordermanagment.OrderManagement.dto.UpdateCustomerRequest;
import com.ordermanagment.OrderManagement.entity.Customer;

import java.util.List;

public interface CustomerService {
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest);
    public Customer getCustomerbyId(Long id);
    public List<Customer> getAllCustomers();
    public void deleteCustomer(Long id);
    public Customer updateCustomer(Long id, UpdateCustomerRequest updateCustomerRequest);
}
