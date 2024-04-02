package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostVisibilityBasedQueryRepository {
    /**
     * `PUBLIC` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostResponseDto> findPublicPosts(Pageable pageable);

    /**
     * `PRIVATE` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostResponseDto> findPrivatePosts(Pageable pageable);
}
