package com.stemm.pubsub.service.post.repository.dto;

import java.time.LocalDateTime;

public record PostRepositoryDto(
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
