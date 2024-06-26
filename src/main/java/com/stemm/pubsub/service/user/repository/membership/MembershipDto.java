package com.stemm.pubsub.service.user.repository.membership;

import java.time.LocalDateTime;

public record MembershipDto(
    Long id,
    String name,
    int monthlyPrice,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {
}
