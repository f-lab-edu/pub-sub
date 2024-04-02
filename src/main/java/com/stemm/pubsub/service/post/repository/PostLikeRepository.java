package com.stemm.pubsub.service.post.repository;

import com.stemm.pubsub.service.post.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
