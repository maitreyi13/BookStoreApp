package com.example.bookstoreapp.service;

import com.example.bookstoreapp.exceptions.BookStoreCustomException;
import com.example.bookstoreapp.model.Email;

public interface IEmailService {

    public String sendEmail(Email email)throws BookStoreCustomException;
    public String getLink(String token);
}