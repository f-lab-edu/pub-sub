package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostVisibilityBasedQueryRepository {
    /**
     * 모든 `PUBLIC` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostResponseDto> findAllPublicPosts(Pageable pageable);

    /**
     * 모든 `PRIVATE` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostResponseDto> findAllPrivatePosts(Pageable pageable);
}
