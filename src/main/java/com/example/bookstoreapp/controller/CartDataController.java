package com.example.bookstoreapp.controller;
import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.CartData;
import com.example.bookstoreapp.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartDataController {
    @Autowired
    private ICartService iCartService;

    @GetMapping(value = {"", "/home"})
    public String greet() {
        return "Welcome to Book Store Application";
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> findAllCarts() {
        Iterable<CartData> allCarts = iCartService.findAllCarts();
        ResponseDTO responseDTO = new ResponseDTO("CartData Details: ", allCarts);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/findCartById/{cartId}")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable("cartId") Long cartId) {
        CartData cartData = iCartService.getCartById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Product details for cart Id "+cartId, cartData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/addToCart/{userId}")
    public ResponseEntity<ResponseDTO> addCartDetails(@PathVariable Long userId, @RequestBody CartDTO cartDTO){
        CartData cartDetails = iCartService.addToCart(userId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details Added", cartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Long userId, @PathVariable Long cartId, @RequestBody CartDTO cartDTO){
        String response = iCartService.editCartByCartId(userId, cartId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Cart Data with Cart ID: "+cartId, response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCart(@PathVariable("cartId") Long cartId) {
        iCartService.deleteCart(cartId);
        ResponseDTO responseDTO = new ResponseDTO("cart number "+cartId+" deleted successfully",cartId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
