package com.stemm.pubsub.service.post.repository.post;

import java.util.Optional;

public interface PostBasicRepository {
    /**
     * 게시물에 대한 좋아요 개수까지 함께 불러옵니다.
     */
    Optional<PostDto> findPostById(Long postId);
}
