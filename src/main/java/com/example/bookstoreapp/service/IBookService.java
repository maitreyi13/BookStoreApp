package com.example.bookstoreapp.service;


import com.example.bookstoreapp.dto.BookDTO;
import com.example.bookstoreapp.model.BookData;

import java.util.List;

public interface IBookService {
    BookData addBook(BookDTO bookDTO);

    List<BookData> getBookList();

    BookData getBookById(Long bookId);
    BookData updateBookQuantity(Long bookId, int bookQuantity);
    List<BookData>getBookByAuthor(String bookAuthor);
    void deleteBook(Long bookId);

    List<BookData> sortBookAscendingOrder();

    List<BookData> sortBookDescendingOrder();
}