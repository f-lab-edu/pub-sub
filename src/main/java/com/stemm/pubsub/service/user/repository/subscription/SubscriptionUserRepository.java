package com.stemm.pubsub.service.user.repository.subscription;

import com.stemm.pubsub.service.user.repository.subscription.dto.SubscriptionUserDto;

public interface SubscriptionUserRepository {
    /**
     * 구독 중인 멤버십을 생성한 유저의 Id를 조회합니다.
     */
    SubscriptionUserDto findSubscribingUsersId(Long subscriberId);
}
