package com.stemm.pubsub.service.post.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostVisibilityRepository {
    /**
     * `PUBLIC` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostDto> findPublicPosts(Pageable pageable);

    /**
     * 특정 유저들의 `PRIVATE` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostDto> findUsersPrivatePosts(List<Long> userIds, Pageable pageable);
}
