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

    /**
     * Retrieves all posts.
     *
     * @return a list of all posts
     */
    @GetMapping(path = "/posts/")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param postId the ID of the post to retrieve
     * @return the post with the given ID
     */
    @GetMapping(path = "/posts/{postId}/")
    public Post getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    /**
     * Creates a new post.
     *
     * @param postObject the post object to create
     * @return an optional containing the created post, or empty if the creation failed
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/posts/")
    public Optional<Post> createPost(@RequestBody Post postObject) {
        return postService.createPost(postObject);
    }

    /**
     * Updates an existing post.
     *
     * @param postId the ID of the post to update
     * @param postObject the updated post object
     * @return an optional containing the updated post, or empty if the update failed
     */
    @PutMapping(path = "/posts/{postId}/")
    public Optional<Post> updatePost(@PathVariable Long postId, @RequestBody Post postObject){
        return postService.updatePost(postId, postObject);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param postId the ID of the post to delete
     * @return a string indicating the result of the deletion
     */
    @DeleteMapping(path = "/posts/{postId}/")
    public String deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }
}
