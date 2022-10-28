package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.BookDTO;
import com.example.bookstoreapp.exceptions.BookStoreCustomException;
import com.example.bookstoreapp.model.BookData;
import com.example.bookstoreapp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookData addBook(BookDTO bookDTO) {
        BookData bookData = new BookData(bookDTO);
        return bookRepository.save(bookData);
    }

    @Override
    public List<BookData> getBookList() {
        return bookRepository.findAll();
    }

    @Override
    public BookData getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreCustomException("Book with id " + bookId + " not found"));
    }
    @Override
    public List<BookData> getBookByAuthor(String bookAuthor) {
        return bookRepository.findBookByBookAuthor(bookAuthor);
    }
    @Override
    public BookData updateBookQuantity(Long bookId, int bookQuantity) {
        BookData bookData = this.getBookById(bookId);
        bookData.setBookQuantity(bookQuantity);
        return bookRepository.save(bookData);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookData> sortBookAscendingOrder() {
        return bookRepository.sortBookAscendingOrder();
    }

    @Override
    public List<BookData> sortBookDescendingOrder() {
        return bookRepository.sortBookDescendingOrder();
    }
}