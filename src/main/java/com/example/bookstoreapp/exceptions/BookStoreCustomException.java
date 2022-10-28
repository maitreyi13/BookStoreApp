package com.example.bookstoreapp.exceptions;

public class BookStoreCustomException extends RuntimeException{
    public BookStoreCustomException(String message) {
        super(message);
    }
}