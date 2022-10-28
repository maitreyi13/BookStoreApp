package com.example.bookstoreapp.repo;
import com.example.bookstoreapp.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

    @Repository
    public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
        @Query(value = "select * from user_registration where first_name= :firstName", nativeQuery = true)
        UserRegistration getUserByName(String firstName);

        @Query(value = "select * from user_registration where password = :password", nativeQuery = true)
        UserRegistration getUserByPassword(String password);

        @Query(value = "select * from user_registration where email= :email", nativeQuery = true)
        UserRegistration getUserByEmail(String email);
    }