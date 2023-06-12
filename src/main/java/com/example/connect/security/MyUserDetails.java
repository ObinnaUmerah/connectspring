/**
 * This class represents the user details for authentication and authorization purposes.
 * It implements the UserDetails interface required by Spring Security.
 */
package com.example.connect.security;

import com.example.connect.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails {
    private final User user;

    /**
     * Constructs a new MyUserDetails object with the specified User instance.
     *
     * @param user The User object representing the user.
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     * This method returns an empty set as no specific authorities are implemented in this example.
     *
     * @return The granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    /**
     * Returns the password for the user.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username (email address) of the user.
     *
     * @return The user's username (email address).
     */
    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    /**
     * Indicates whether the user's account has expired.
     * This method always returns true as no account expiration logic is implemented in this example.
     *
     * @return True if the user's account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     * This method always returns true as no account locking logic is implemented in this example.
     *
     * @return True if the user's account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * This method always returns true as no credential expiration logic is implemented in this example.
     *
     * @return True if the user's credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * This method always returns true as no user enable/disable logic is implemented in this example.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the User object associated with this user details.
     *
     * @return The User object.
     */
    public User getUser() {
        return user;
    }
}
