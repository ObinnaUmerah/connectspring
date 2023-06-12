package com.example.connect.service;

import com.example.connect.exception.InformationExistException;
import com.example.connect.exception.InformationNotFoundException;
import com.example.connect.model.Post;
import com.example.connect.model.User;
import com.example.connect.repository.PostRepository;
import com.example.connect.repository.UserRepository;
import com.example.connect.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostService{

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public List<Post> getAllPosts(){
//        User user = getCurrentLoggedInUser();
        List<Post> postList = postRepository.findAll();
        if(postList.isEmpty()){
            throw new InformationNotFoundException("No post found");
        }
        else{
            return postList;
        }
    }

    public Post getPost(Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    public Optional<Post> createPost(Post postObject){
        postObject.setUser(PostService.getCurrentLoggedInUser());
        return Optional.of(postRepository.save(postObject));
    }


    public Optional<Post> updatePost(Long postId, Post postObject) {
        Optional<Post> post = Optional.ofNullable(postRepository.findByIdAndUserId(postId, PostService.getCurrentLoggedInUser().getId()));
        if(post.isPresent()){
            post.get().setContent(postObject.getContent());
            return Optional.of(postRepository.save(post.get()));
        } else {
            throw new InformationNotFoundException("Post doesn't exist.");
        }
    }

    public String deletePost(Long postId){
        Optional<Post> post = Optional.ofNullable(postRepository.findByIdAndUserId(postId, PostService.getCurrentLoggedInUser().getId()));
        if(post.isPresent()) {
            postRepository.deleteById(postId);
            return "Post with id " + postId + " was deleted";
        } else {
            throw new InformationNotFoundException("Post with id: " + postId + " doesn't exist");

        }
    }

    @PostMapping(path ="/posts/")
    public Post createPost(@RequestBody Post postObject, @PathVariable Long userid) {
       Optional<User> user = userRepository.findById(userid);

       Post post = new Post();
       return post;
    }
}
