package com.example.connect.controller;

import com.example.connect.model.Post;
import com.example.connect.model.User;
import com.example.connect.security.MyUserDetails;
import com.example.connect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class PostController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
    }


    @GetMapping(path = "/posts/")
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping(path = "/posts/{postId}/")
    public Post getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/posts/")
    public Optional<Post> createPost(@RequestBody Post postObject) {
        return postService.createPost(postObject);
    }

    @PutMapping(path = "/posts/{postId}/")
    public Optional<Post> updatePost(@PathVariable Long postId, @RequestBody Post postObject){
        return postService.updatePost(postId, postObject);
    }

    @DeleteMapping(path = "/posts/{postId}/")
    public String deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }
}
