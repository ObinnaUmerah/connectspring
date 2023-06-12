/**
 * This class represents a service for managing posts in a social media application.
 */
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
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    /**
     * Sets the post repository.
     *
     * @param postRepository The post repository to be set.
     */
    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Sets the user repository.
     *
     * @param userRepository The user repository to be set.
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The currently logged-in user.
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Retrieves all posts.
     *
     * @return A list of all posts.
     * @throws InformationNotFoundException if no posts are found.
     */
    public List<Post> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        if (postList.isEmpty()) {
            throw new InformationNotFoundException("No post found");
        } else {
            return postList;
        }
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The post with the specified ID, or null if not found.
     */
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    /**
     * Creates a new post.
     *
     * @param postObject The post object to create.
     * @return The created post wrapped in an Optional.
     */
    public Optional<Post> createPost(Post postObject) {
        postObject.setUser(PostService.getCurrentLoggedInUser());
        return Optional.of(postRepository.save(postObject));
    }

    /**
     * Updates an existing post.
     *
     * @param postId      The ID of the post to update.
     * @param postObject  The updated post object.
     * @return The updated post wrapped in an Optional.
     * @throws InformationNotFoundException if the post doesn't exist.
     */
    public Optional<Post> updatePost(Long postId, Post postObject) {
        Optional<Post> post = Optional.ofNullable(postRepository.findByIdAndUserId(postId, PostService.getCurrentLoggedInUser().getId()));
        if (post.isPresent()) {
            post.get().setContent(postObject.getContent());
            return Optional.of(postRepository.save(post.get()));
        } else {
            throw new InformationNotFoundException("Post doesn't exist.");
        }
    }

    /**
     * Deletes a post.
     *
     * @param postId The ID of the post to delete.
     * @return A string indicating the status of the operation.
     * @throws InformationNotFoundException if the post doesn't exist.
     */
    public String deletePost(Long postId) {
        Optional<Post> post = Optional.ofNullable(postRepository.findByIdAndUserId(postId, PostService.getCurrentLoggedInUser().getId()));
        if (post.isPresent()) {
            postRepository.deleteById(postId);
            return "Post with id " + postId + " was deleted";
        } else {
            throw new InformationNotFoundException("Post with id: " + postId + " doesn't exist");
        }
    }

    /**
     * Creates a new post.
     *
     * @param postObject The post object to create.
     * @param userid     The ID of the user associated with the post.
     * @return The created post.
     */
    @PostMapping(path = "/posts/")
    public Post createPost(@RequestBody Post postObject, @PathVariable Long userid) {
        Optional<User> user = userRepository.findById(userid);

        Post post = new Post();
        return post;
    }
}
