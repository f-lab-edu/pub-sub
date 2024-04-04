package com.stemm.pubsub.service.user.repository.subscription;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.user.repository.subscription.dto.SubscriptionUserDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.stemm.pubsub.service.user.entity.QUser.user;
import static com.stemm.pubsub.service.user.entity.subscription.QSubscription.subscription;
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;

public class SubscriptionUserRepositoryImpl implements SubscriptionUserRepository {

    private final JPAQueryFactory queryFactory;

    public SubscriptionUserRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public SubscriptionUserDto findSubscribingUsersId(Long subscriberId) {
        List<Long> userIds = queryFactory
            .select(user.id)
            .from(subscription)
            .join(user).on(subscription.membership.id.eq(user.membership.id))
            .where(subscription.user.id.eq(subscriberId), subscription.status.eq(ACTIVE))
            .fetch();

        return new SubscriptionUserDto(userIds);
    }
}
