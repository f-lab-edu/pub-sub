package com.stemm.pubsub.service.user.repository.subscription;

import com.stemm.pubsub.service.user.entity.subscription.Subscription;

import java.util.List;

public interface SubscriptionTimeRepository {
    List<Subscription> findNewestSubscriptions(Long userId);
    List<Subscription> findOldestSubscriptions(Long userId);
}
