package com.example.connect.repository;

import com.example.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Login
    boolean existsByEmailAddress(String email);

    //Register
    User findUserByEmailAddress(String email);

    Optional<User> findById(Long id);
}
