package com.stemm.pubsub.service.user.repository.dto;

import java.time.LocalDateTime;

public record MembershipRepositoryDto(
    Long id,
    String name,
    int monthlyPrice,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}