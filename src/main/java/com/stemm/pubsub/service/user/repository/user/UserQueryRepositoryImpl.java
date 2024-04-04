package com.stemm.pubsub.service.user.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.user.entity.QUser.user;

public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public UserDto findByUserId(Long userId) {
        return queryFactory
            .select(
                constructor(
                    UserDto.class,
                    user.id,
                    user.membership.id,
                    user.nickname,
                    user.email,
                    user.profileImageUrl,
                    user.bio
                )
            )
            .from(user)
            .where(user.id.eq(userId))
            .fetchOne();
    }

    @Override
    public UserDto findByNickname(String nickname) {
        return queryFactory
            .select(
                constructor(
                    UserDto.class,
                    user.id,
                    user.membership.id,
                    user.nickname,
                    user.email,
                    user.profileImageUrl,
                    user.bio
                )
            )
            .from(user)
            .where(user.nickname.eq(nickname))
            .fetchOne();
    }
}
