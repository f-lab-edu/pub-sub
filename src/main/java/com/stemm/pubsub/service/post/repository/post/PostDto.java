package com.stemm.pubsub.service.post.repository.post;

import java.time.LocalDateTime;

public record PostDto(
    Long id,
    String nickname,
    String profileImageUrl,
    String content,
    String imageUrl,
    int likeCount,
    int dislikeCount,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}
