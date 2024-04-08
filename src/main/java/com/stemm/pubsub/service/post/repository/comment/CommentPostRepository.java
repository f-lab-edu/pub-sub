package com.stemm.pubsub.service.post.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentPostRepository {
    /**
     * 특정 게시물의 댓글을 좋아요 개수 순으로 조회합니다.
     */
    Page<CommentDto> findCommentsForPost(Long postId, Pageable pageable);
}
