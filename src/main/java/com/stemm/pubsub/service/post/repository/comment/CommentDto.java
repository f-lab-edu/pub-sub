package com.stemm.pubsub.service.post.repository.comment;

import java.time.LocalDateTime;

public record CommentDto(
    Long id,
    String nickname,
    String profileImageUrl,
    String content,
    int likeCount,
    int dislikeCount,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}

