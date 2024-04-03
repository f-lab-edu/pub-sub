package com.stemm.pubsub.service.user.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.user.repository.dto.UserRepositoryDto;
import jakarta.persistence.EntityManager;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.user.entity.QUser.user;

public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public UserRepositoryDto findUserById(Long userId) {
        return queryFactory
            .select(
                constructor(
                    UserRepositoryDto.class,
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
    public UserRepositoryDto findUserByNickname(String nickname) {
        return queryFactory
            .select(
                constructor(
                    UserRepositoryDto.class,
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
