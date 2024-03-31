package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSubscriptionBasedQueryRepository {
    // TODO: 각 설명 주석 추가
    Page<Post> findAllPosts(Long userId, Pageable pageable);
    Page<Post> findPostsBasedOnSubscriptions(Long userId, Pageable pageable);
}
