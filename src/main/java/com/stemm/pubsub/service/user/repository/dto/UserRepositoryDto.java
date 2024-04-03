package com.stemm.pubsub.service.user.repository.dto;

public record UserRepositoryDto(
    Long id,
    Long membershipId,
    String nickname,
    String email,
    String profileImageUrl,
    String bio
) {
}
