/**
 * This class represents a service for managing users in a social media application.
 */
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
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService instance.
     *
     * @param userRepository        The user repository.
     * @param passwordEncoder       The password encoder.
     * @param jwtUtils              The JWT utility class.
     * @param authenticationManager The authentication manager.
     * @param myUserDetails         The custom user details implementation.
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    /**
     * Creates a new user.
     *
     * @param userObject The user object to create.
     * @return The created user.
     * @throws InformationExistException if a user with the same email address already exists.
     */
    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("User with email address " + userObject.getEmailAddress() +
                    " already exists");
        }
    }

    /**
     * Finds a user by email address.
     *
     * @param email The email address of the user to find.
     * @return The user with the specified email address.
     */
    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginRequest The login request containing the user's email and password.
     * @return A response entity containing the generated JWT token.
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();

            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e) {
            return ResponseEntity.ok(new LoginResponse("Error: username or password is incorrect"));
        }
    }
}
