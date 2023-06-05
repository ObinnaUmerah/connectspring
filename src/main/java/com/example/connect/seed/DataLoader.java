package com.example.connect.seed;

import com.example.connect.model.User;
import com.example.connect.repository.UserRepository;
import com.example.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User(1L, "jack@aol.com", "jack123");
            User user2 = new User(2L, "rachel", "rachel123");
            User user3 = new User(3L, "Liane", "liane123");
            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);
        }
    }
}
