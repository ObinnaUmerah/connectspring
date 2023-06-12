/**
 * This class is an implementation of the UserDetailsService interface required by Spring Security.
 * It loads user-specific data when authentication is performed.
 */
package com.example.connect.security;

import com.example.connect.model.User;
import com.example.connect.security.MyUserDetails;
import com.example.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;

    /**
     * Sets the UserService dependency using autowiring.
     *
     * @param userService The UserService instance.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads the user details for the specified username (email address).
     *
     * @param email The email address used to find the user.
     * @return The UserDetails object representing the user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new MyUserDetails(user);
    }
}
