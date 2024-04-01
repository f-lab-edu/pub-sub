package com.stemm.pubsub.service.post.dto.response;

import java.time.LocalDateTime;

public record PostResponseDto(
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
