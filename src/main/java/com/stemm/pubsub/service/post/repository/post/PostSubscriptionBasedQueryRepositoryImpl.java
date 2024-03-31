package com.stemm.pubsub.service.post.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.post.entity.post.Post;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PostSubscriptionBasedQueryRepositoryImpl implements PostSubscriptionBasedQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostSubscriptionBasedQueryRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Post> findAllPosts(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findPostsBasedOnSubscriptions(Long userId, Pageable pageable) {
        return null;
    }
}
