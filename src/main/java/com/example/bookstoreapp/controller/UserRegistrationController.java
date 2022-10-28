package com.example.bookstoreapp.controller;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserRegistrationDTO;
import com.example.bookstoreapp.model.UserRegistration;
import com.example.bookstoreapp.service.IUserRegistrationService;
import com.example.bookstoreapp.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userRegistration")
public class UserRegistrationController {
    @Autowired
    private IUserRegistrationService iUserRegistrationService;
    @Autowired
    TokenUtility tokenUtility;

    @GetMapping(value = {"", "/home"})
    public String greet() {
        return "Welcome to Book Store Application";
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDTO> createUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistration response = iUserRegistrationService.createUserRegistration(userRegistrationDTO);
        ResponseDTO respDTO = new ResponseDTO("Data Added Successfully and email sent to the User", response);
        return new ResponseEntity<>(respDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationData(@PathVariable("token") String token,
                                                                  @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistration userRegistrationData = iUserRegistrationService.updateUserRegistration(token, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Data : ", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/printAll")
    public ResponseEntity<ResponseDTO> getUserData() {
        List<UserRegistration> usersData = iUserRegistrationService.getAllUserData();
        System.out.println(usersData.toString());
        ResponseDTO response = new ResponseDTO("Data: ", usersData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDTO> deleteContactData(@PathVariable("token") String token) {
        iUserRegistrationService.deleteUser(token);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully", "Deleted User: " + getUserDetails(token));
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("userId") Long userId) {
        UserRegistration userRegistrationData;
        userRegistrationData = iUserRegistrationService.getUserRegistrationByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("User Data For Id "+userId+" :", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getByName/{firstName}")
    public ResponseEntity<ResponseDTO> getUserByName(@PathVariable("firstName") String firstName) {
        UserRegistration userRegistrationData = iUserRegistrationService.getUserByName(firstName);
        ResponseDTO responseDTO = new ResponseDTO("User Data For "+firstName+" :", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/forgotPassword/{userId}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable("userId") Long userId,@RequestParam("email") String email) {
        UserRegistration userRegistrationData = iUserRegistrationService.forgotUserPassword(userId,email);
        ResponseDTO responseDTO = new ResponseDTO("User Data For "+userId+" :", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/userLogin")
    public ResponseEntity<ResponseDTO> getUserLogin(@RequestParam("userId") Long userId, @RequestParam("password") String password) {
        UserRegistration userRegistrationData = iUserRegistrationService.getUserLogin(userId, password);
        ResponseDTO responseDTO = new ResponseDTO("User Data For " + userId + " :", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/loginByToken")
    public ResponseEntity<ResponseDTO> getUserLoginByToken(@RequestParam("token") String token) {
        Optional<UserRegistration> userRegistrationData = iUserRegistrationService.getUserLoginToken(token);
        ResponseDTO responseDTO = new ResponseDTO("User Data :", userRegistrationData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/getUser/{token}")
    public ResponseEntity<ResponseDTO> getUserDetails(@PathVariable String token){
        UserRegistration userData = iUserRegistrationService.getUserDataByToken(token);
        Long Userid = tokenUtility.decodeToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully for the ID: "+Userid, userData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //verify token
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token){
        ResponseEntity<ResponseDTO> user= iUserRegistrationService.verify(token);
        ResponseDTO responseDto=new ResponseDTO("user verified successfully",user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}