package com.stemm.pubsub.service.user.repository.subscription;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.user.entity.subscription.Subscription;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.stemm.pubsub.service.user.entity.subscription.QSubscription.subscription;
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;

public class CustomSubscriptionRepositoryImpl implements CustomSubscriptionRepository {

    private final JPAQueryFactory queryFactory;

    public CustomSubscriptionRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Subscription> findNewestSubscriptions(Long userId) {
        return queryFactory
            .selectFrom(subscription)
            .where(userIdEquals(userId), isActive())
            .orderBy(subscription.createdDate.desc())
            .fetch();
    }

    @Override
    public List<Subscription> findOldestSubscriptions(Long userId) {
        return queryFactory
            .selectFrom(subscription)
            .where(userIdEquals(userId), isActive())
            .orderBy(subscription.createdDate.asc())
            .fetch();
    }

    private BooleanExpression userIdEquals(Long userId) {
        return subscription.user.id.eq(userId);
    }

    private BooleanExpression isActive() {
        return subscription.status.eq(ACTIVE);
    }
}
