package com.ordermanagment.OrderManagement.repository;

import com.ordermanagment.OrderManagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
