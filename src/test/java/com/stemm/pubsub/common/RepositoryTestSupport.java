package com.stemm.pubsub.common;

import com.stemm.pubsub.service.user.repository.MembershipRepository;
import com.stemm.pubsub.service.user.repository.UserRepository;
import com.stemm.pubsub.service.user.repository.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTestWithAuditing
public abstract class RepositoryTestSupport {
    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;
}
