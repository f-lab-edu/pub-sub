package com.stemm.pubsub.service.post.repository.dto;

import java.time.LocalDateTime;

public record CommentRepositoryDto(
    Long id,
    Long postId,
    String nickname,
    String profileImageUrl,
    String content,
    int likeCount,
    int dislikeCount,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}

