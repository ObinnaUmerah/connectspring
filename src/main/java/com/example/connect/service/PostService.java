package com.example.connect.service;

import com.example.connect.model.Post;
import com.example.connect.model.User;
import com.example.connect.repository.PostRepository;
import com.example.connect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class PostService{

    private PostRepository postRepository;
    private UserRepository userRepository;

//    @PostMapping(path ="/posts/")
//    public Post createPost(@RequestBody Post postObject, @PathVariable Long userid) {
//       Optional<User> user = userRepository.findById(userid);
//
//       Post post = new Post();
//       return post;
//    }
}
