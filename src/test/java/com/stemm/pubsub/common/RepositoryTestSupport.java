package com.stemm.pubsub.common;

import com.stemm.pubsub.common.config.QuerydslConfig;
import com.stemm.pubsub.service.post.repository.comment.CommentRepository;
import com.stemm.pubsub.service.post.repository.post.PostRepository;
import com.stemm.pubsub.service.post.repository.postlike.PostLikeRepository;
import com.stemm.pubsub.service.user.repository.membership.MembershipRepository;
import com.stemm.pubsub.service.user.repository.subscription.SubscriptionRepository;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTestWithAuditing
@Import(QuerydslConfig.class)
public abstract class RepositoryTestSupport {
    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PostLikeRepository postLikeRepository;

    @Autowired
    protected CommentRepository commentRepository;
}
