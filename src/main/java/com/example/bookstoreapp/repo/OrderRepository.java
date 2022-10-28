package com.example.bookstoreapp.repo;

import com.example.bookstoreapp.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrderRepository extends JpaRepository<OrderData, Long> {
}