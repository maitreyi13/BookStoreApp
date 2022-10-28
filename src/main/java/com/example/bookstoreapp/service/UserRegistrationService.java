package com.example.bookstoreapp.service;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserRegistrationDTO;
import com.example.bookstoreapp.exceptions.BookStoreCustomException;
import com.example.bookstoreapp.model.Email;
import com.example.bookstoreapp.model.UserRegistration;
import com.example.bookstoreapp.repo.UserRegistrationRepository;
import com.example.bookstoreapp.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationService implements IUserRegistrationService {
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private EmailService emailService;
    @Override
    public UserRegistration getUserRegistrationByUserId(Long userId) {
        return userRegistrationRepository.findById(userId)
                .orElseThrow(() -> new BookStoreCustomException("User not found"));
    }

    @Override
    public UserRegistration getUserByName(String firstName) {
        UserRegistration userRegistrationData = userRegistrationRepository.getUserByName(firstName);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new BookStoreCustomException("User with name " + firstName + " not found");
    }

    public UserRegistration createUserRegistration(UserRegistrationDTO userRegistrationDTO){
        UserRegistration user = userRegistrationRepository.save(new UserRegistration(userRegistrationDTO));
        String token=tokenUtility.createToken(user.getUserId());
        user.setToken(token);
        Email email = new Email(user.getEmail(),"user is registered",user.getFirstName()+"=>"+emailService.getLink(token));
        try {
            emailService.sendEmail(email);
        } catch (BookStoreCustomException e) {
            throw new RuntimeException(e);
        }
        return userRegistrationRepository.save(user);
    }

    @Override
    public UserRegistration updateUserRegistration(String token, UserRegistrationDTO userRegistrationDTO) {
        Long userId = tokenUtility.decodeToken(token);
        UserRegistration existingGreet = userRegistrationRepository.findById(userId).orElse(null);
        if (existingGreet != null) {
            existingGreet.setFirstName(userRegistrationDTO.getFirstName());
            existingGreet.setLastName(userRegistrationDTO.getLastName());
            existingGreet.setEmail(userRegistrationDTO.getEmail());
            existingGreet.setPassword(userRegistrationDTO.getPassword());
            existingGreet.setAddress(userRegistrationDTO.getAddress());
        }
        assert existingGreet != null;
        return userRegistrationRepository.save(existingGreet);
    }

    public void deleteUser(String token) {
        Long userId = tokenUtility.decodeToken(token);
        userRegistrationRepository.deleteById(userId);
    }

    @Override
    public List<UserRegistration> getAllUserData() {
        if (userRegistrationRepository.findAll().isEmpty()) {
            System.out.println("No data found");
            throw new BookStoreCustomException("No contact in database.");

        } else return userRegistrationRepository.findAll();
    }

    public UserRegistration getUserByPassword(String password) {
        UserRegistration userRegistrationData = userRegistrationRepository.getUserByPassword(password);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new BookStoreCustomException("User with name " + password + " not found");
    }

    @Override
    public UserRegistration getUserLogin(Long userId, String password) {
        if (userRegistrationRepository.findById(userId).isPresent()) {
            getUserByPassword(password);
        } else throw new BookStoreCustomException("User Id or password is incorrect");
        return getUserByPassword(password);
    }

    @Override
    public UserRegistration forgotUserPassword(Long userId, String email) {
        UserRegistration userRegistrationData = userRegistrationRepository.getUserByEmail(email);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new BookStoreCustomException("User with email id " + userId + " not found");
    }

    @Override
    public UserRegistration getUserDataByToken(String token) {
        Long UserId = tokenUtility.decodeToken(token);
        Optional<UserRegistration> existingData = userRegistrationRepository.findById(UserId);
        if (existingData.isPresent()) {
           return existingData.get();
        } else
            throw new BookStoreCustomException("Invalid Token");
    }

    @Override
    public Optional<UserRegistration> getUserLoginToken(String token) {
        Long userId = tokenUtility.decodeToken(token);
        if (userRegistrationRepository.findById(userId).isPresent()) {
            getUserRegistrationByUserId(userId);
        } else throw new BookStoreCustomException("Token is incorrect");
        return userRegistrationRepository.findById(Long.valueOf(token));
    }
    public ResponseEntity<ResponseDTO> verify(String token) {
        Optional<UserRegistration> user=userRegistrationRepository.findById((long) Math.toIntExact(tokenUtility.decodeToken(token)));
        if (user.isEmpty()) {
            ResponseDTO responseDTO = new ResponseDTO("ERROR: Invalid token", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        user.get().setVerified(true);
        userRegistrationRepository.save(user.get());
        ResponseDTO responseDTO = new ResponseDTO(" The user has been verified ", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}