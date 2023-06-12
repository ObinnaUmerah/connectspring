/**
 * This class is responsible for seeding initial data into the application.
 */
package com.example.connect.seed;

import com.example.connect.model.Post;
import com.example.connect.model.User;
import com.example.connect.repository.PostRepository;
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
    PostRepository postRepository;

    @Autowired
    UserService userService;

    /**
     * Runs the data loading process when the application starts.
     *
     * @param args The command-line arguments.
     * @throws Exception if an error occurs during the data loading process.
     */
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    /**
     * Loads initial user data into the application.
     */
    private void loadUserData() {
        System.out.println("Loading data...");
        if (userRepository.count() == 0) {
            User user1 = new User(1L, "jack@aol.com", "jack123");
            User user2 = new User(2L, "ron@aol.com", "ron123");
            User user3 = new User(3L, "luke@aol.com", "luke123");
            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);

            Post post1 = new Post(1L, "Hello Peeps!");
            Post post2 = new Post(2L, "What's for dinner?");
            Post post3 = new Post(3L, "This is war, get up and fight.");
            Post post4 = new Post(4L, "Where is she?");
            post1.setUser(user1);
            post2.setUser(user1);
            post3.setUser(user2);
            post4.setUser(user3);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
            postRepository.save(post4);
            System.out.println("Data completely loaded.");
        }
    }
}
