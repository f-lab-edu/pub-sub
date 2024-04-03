package com.stemm.pubsub.service.user.repository.dto;

import java.time.LocalDateTime;

public record UserRepositoryDto(
    Long id,
    Long membershipId,
    String nickname,
    String email,
    String profileImageUrl,
    String bio,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}
