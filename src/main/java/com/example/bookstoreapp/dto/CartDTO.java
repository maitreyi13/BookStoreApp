package com.example.bookstoreapp.dto;
import lombok.Data;
import org.springframework.stereotype.Component;
@Component
@Data
public class CartDTO {
    public Long bookId;
    public int quantity;
}