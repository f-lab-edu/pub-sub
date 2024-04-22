package com.stemm.pubsub.common;

import com.stemm.pubsub.service.post.repository.post.PostRepository;
import com.stemm.pubsub.service.post.service.PostService;
import com.stemm.pubsub.service.user.repository.membership.MembershipRepository;
import com.stemm.pubsub.service.user.repository.subscription.SubscriptionRepository;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PostService postService;
}
