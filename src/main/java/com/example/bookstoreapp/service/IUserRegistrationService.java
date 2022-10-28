package com.example.bookstoreapp.service;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserRegistrationDTO;
import com.example.bookstoreapp.model.UserRegistration;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {

    UserRegistration getUserRegistrationByUserId(Long userId);

    UserRegistration createUserRegistration(UserRegistrationDTO userRegistrationDTO);

    UserRegistration updateUserRegistration(String token, UserRegistrationDTO userRegistrationDTO);

    UserRegistration getUserByName(String firstName);
    void deleteUser(String token);
    List<UserRegistration> getAllUserData();
    UserRegistration getUserLogin(Long userId, String password);
    UserRegistration forgotUserPassword(Long userId, String email);
    UserRegistration getUserDataByToken(String token);
    Optional<UserRegistration> getUserLoginToken(String token);
    ResponseEntity<ResponseDTO> verify(String token);
}