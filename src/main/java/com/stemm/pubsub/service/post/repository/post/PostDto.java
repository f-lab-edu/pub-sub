package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.entity.post.Visibility;

import java.time.LocalDateTime;

public record PostDto(
    Long id,
    Long userId,
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
