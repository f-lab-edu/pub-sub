package com.stemm.pubsub.service.post.dto;

import com.stemm.pubsub.service.post.entity.post.Visibility;

import java.time.LocalDateTime;

public record PostResponse(
    Long id,
    String nickname,
    String profileImageUrl,
    String content,
    String imageUrl,
    Visibility visibility,
    int likeCount,
    int dislikeCount,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}
