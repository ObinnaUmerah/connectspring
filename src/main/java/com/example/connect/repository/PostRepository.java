package com.example.connect.repository;

import com.example.connect.model.Post;
import com.example.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    <Optionial> Post findByIdAndUserId(Long postId, Long userId);
}
