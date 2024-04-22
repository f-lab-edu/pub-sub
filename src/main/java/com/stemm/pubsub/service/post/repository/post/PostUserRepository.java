package com.stemm.pubsub.service.post.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostUserRepository {
    Page<PostDto> findPublicPostsByUserId(Long userId, Pageable pageable);
    Page<PostDto> findPrivatePostsByUserId(Long userId, Pageable pageable);

    Page<PostDto> findPublicPostsByUserIds(List<Long> userIds, Pageable pageable);
    Page<PostDto> findPrivatePostsByUserIds(List<Long> userIds, Pageable pageable);
}
