package com.example.connect.repository;

import com.example.connect.model.Post;
import com.example.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    <Optional> Post findByIdAndUserId(Long postId, Long userId);

    Optional<List<Post>> findAllByUserId(Long userId);
}
