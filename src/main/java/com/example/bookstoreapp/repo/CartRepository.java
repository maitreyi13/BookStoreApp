package com.example.bookstoreapp.repo;
import com.example.bookstoreapp.model.CartData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartData, Long> {
}
