package com.example.bookstoreapp.service;
import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.model.CartData;

import java.util.List;

public interface ICartService {
    CartData addToCart(Long userId, CartDTO cartDTO);

    List<CartData> findAllCarts();

    CartData getCartById(Long cartId);
    String  editCartByCartId(Long userId, Long cartId, CartDTO cartDTO);
    void deleteCart(Long cartId);
}