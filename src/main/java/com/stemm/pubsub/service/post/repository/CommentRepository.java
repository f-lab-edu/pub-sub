package com.stemm.pubsub.service.post.repository;

import com.stemm.pubsub.service.post.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Post, Long> {
}
