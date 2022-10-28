package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.BookDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.BookData;
import com.example.bookstoreapp.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookData")
public class BookDataController
{
    @Autowired
    private IBookService iBookService;

    @GetMapping(value = {"", "/home"})
    public String greet() {
        return "Welcome to Book Store Application";
    }

    @GetMapping("/getBooks")
    public ResponseEntity<ResponseDTO> getBooksList() {
        List<BookData> bookDataList = iBookService.getBookList();
        ResponseDTO responseDTO = new ResponseDTO("Books Available in book store: ", bookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/findBookById/{bookId}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("bookId") Long bookId) {
        BookData bookData = iBookService.getBookById(bookId);
        ResponseDTO responseDTO = new ResponseDTO("Book Data For Book Id "+bookId+" :", bookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orderAscend")
    public ResponseEntity<ResponseDTO> sortBookAscendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookAscendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Books ordered from A-Z", bookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orderDescend")
    public ResponseEntity<ResponseDTO> sortBookDescendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookDescendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Books ordered from Z-A", bookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookData bookData = iBookService.addBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book added successfully", bookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updateQuantity/{bookId}")
    public ResponseEntity<ResponseDTO> updateBookQuantity(@PathVariable("bookId") Long bookId,
                                                          @RequestParam(value = "bookQuantity") int bookQuantity) {
        BookData bookData = iBookService.updateBookQuantity(bookId, bookQuantity);
        ResponseDTO responseDTO = new ResponseDTO("Successfully updated book quantity for id " + bookId, bookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDTO> deleteContactData(@PathVariable Long bookId) {
        iBookService.deleteBook(bookId);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully", "Deleted id: " + bookId);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
}