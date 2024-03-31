package com.stemm.pubsub.service.user.repository.subscription;

import com.stemm.pubsub.service.user.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends
    JpaRepository<Subscription, Long>,
    SubscriptionTimeBasedQueryRepository {
}
