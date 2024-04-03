package com.stemm.pubsub.service.user.repository.dto;

import com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus;

import java.time.LocalDateTime;

public record SubscriptionRepositoryDto(
    Long id,
    Long userId,
    Long membershipId,
    SubscriptionStatus status,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}
