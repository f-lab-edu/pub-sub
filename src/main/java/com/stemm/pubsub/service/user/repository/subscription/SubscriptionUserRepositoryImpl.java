package com.stemm.pubsub.service.user.repository.subscription;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.user.repository.subscription.dto.SubscriptionUserDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.stemm.pubsub.service.user.entity.QUser.user;
import static com.stemm.pubsub.service.user.entity.subscription.QSubscription.subscription;
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;

@RequiredArgsConstructor
public class SubscriptionUserRepositoryImpl implements SubscriptionUserRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public SubscriptionUserDto findSubscribingUserIds(Long subscriberId) {
        List<Long> userIds = queryFactory
            .select(user.id)
            .from(subscription)
            .join(user).on(subscription.membership.id.eq(user.membership.id))
            .where(subscription.user.id.eq(subscriberId), subscription.status.eq(ACTIVE))
            .fetch();

        return new SubscriptionUserDto(userIds);
    }
}
