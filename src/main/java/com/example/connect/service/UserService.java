package com.example.connect.service;

import com.example.connect.exception.InformationExistException;
import com.example.connect.model.User;
import com.example.connect.model.request.LoginRequest;
import com.example.connect.model.response.LoginResponse;
import com.example.connect.repository.UserRepository;
import com.example.connect.security.JWTUtils;
import com.example.connect.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private JWTUtils jwtUtils;
    private MyUserDetails myUserDetails;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user with email address " + userObject.getEmailAddress() +
                    " already exists");
        }
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }


    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();

            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e) {
            return ResponseEntity.ok(new LoginResponse("Error : username or password is incorrect"));
        }
    }
}
