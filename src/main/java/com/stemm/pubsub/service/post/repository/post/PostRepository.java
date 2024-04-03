package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostVisibilityRepository {
}
