package com.stemm.pubsub.service.user.repository.user;

public record UserDto(
    Long id,
    Long membershipId,
    String nickname,
    String email,
    String profileImageUrl,
    String bio
) {
}
