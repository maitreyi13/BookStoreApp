package com.example.bookstoreapp.service;
import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.exceptions.BookStoreCustomException;
import com.example.bookstoreapp.model.BookData;
import com.example.bookstoreapp.model.CartData;
import com.example.bookstoreapp.model.UserRegistration;
import com.example.bookstoreapp.repo.BookRepository;
import com.example.bookstoreapp.repo.CartRepository;
import com.example.bookstoreapp.repo.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CartService implements ICartService{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    BookRepository bookRepository;

    @Override
    public CartData addToCart(Long userId, CartDTO cartDTO){
        Optional<UserRegistration> userData = userRegistrationRepository.findById(userId);
        Optional<BookData> bookData = bookRepository.findById(cartDTO.getBookId());
        if(userData.isPresent()){
            if(bookData.isPresent()) {
                if(cartDTO.getQuantity()<=bookData.get().getBookQuantity()){
                    double totalPrice = bookData.get().getBookPrice()*cartDTO.getQuantity();
                    CartData cartDetails = new CartData(userData.get(), bookData.get(), cartDTO.getQuantity(),totalPrice);
                    return cartRepository.save(cartDetails);
                }else
                     throw new BookStoreCustomException("Quantity Exceeds, Available Book Quantity: "+bookData.get().getBookQuantity());
            }else
                throw new BookStoreCustomException("Book Does not exist: Invalid BookId");
        }else
            throw new BookStoreCustomException("User Does not exist: Invalid UserId");
    }
    @Override
    public List<CartData> findAllCarts() {
        List<CartData> cartList = cartRepository.findAll();
        if(cartList.isEmpty()){
            throw new BookStoreCustomException("No Items added in cart yet!!");
        }else
            return cartList;
    }
    @Override
    public CartData getCartById(Long cartId) {
        Optional<CartData> cartDetails = cartRepository.findById(cartId);
        if (cartDetails.isPresent()) {
            return cartDetails.get();
        } else
            throw new BookStoreCustomException("Cart ID does not exist: Invalid ID");
    }
    @Override
    public String editCartByCartId(Long userId, Long cartId, CartDTO cartDTO) {
        Optional<UserRegistration> userData = userRegistrationRepository.findById(userId);
        Optional<CartData> cartDetails = cartRepository.findById(cartId);
        Optional<BookData> bookDetails = bookRepository.findById(cartDTO.getBookId());
        if(cartDetails.isPresent() && userData.isPresent()){
            if(bookDetails.isPresent()){
                cartDetails.get().setBook(bookDetails.get());
                if(cartDTO.getQuantity()<=bookDetails.get().getBookQuantity()){
                    cartDetails.get().setQuantity(cartDTO.getQuantity());
                    cartDetails.get().setTotalPrice(cartDTO.getQuantity()*bookDetails.get().getBookPrice());
                    cartRepository.save(cartDetails.get());
                    return "Cart Details Updated Quantity: "+cartDTO.getQuantity()+" Total Price: "+cartDetails.get().getTotalPrice();
                }else
                    throw new BookStoreCustomException("Quantity Exceeds, Available Book Quantity: "+bookDetails.get().getBookQuantity());
            }else
                throw new BookStoreCustomException("Book ID does not exist: Invalid Book ID");
        }else
            throw new BookStoreCustomException("Invalid Cart ID or User ID!");
    }
    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
