package com.stemm.pubsub.service.post.dto;

import java.time.LocalDateTime;

public record PostDto(
    Long id,
    String nickname,
    String content,
    String imageUrl,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate,
    int likeCount,
    int dislikeCount
) {
}
