package com.orders.orderservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.orders.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAll(Pageable pageable);
    Page<Order> findAllByCustomerName(String customerName, Pageable pageable);
    Page<Order> findAllByStatus(String status, Pageable pageable);

}
