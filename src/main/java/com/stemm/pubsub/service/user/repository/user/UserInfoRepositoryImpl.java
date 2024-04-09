package com.stemm.pubsub.service.user.repository.user;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.user.entity.QUser.user;

@RequiredArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private final JPAQueryFactory queryFactory;

    private final ConstructorExpression<UserDto> userDto = constructor(
        UserDto.class,
        user.id,
        user.membership.id,
        user.nickname,
        user.email,
        user.profileImageUrl,
        user.bio
    );

    @Override
    public UserDto findUserById(Long userId) {
        return queryFactory
            .select(userDto)
            .from(user)
            .where(user.id.eq(userId))
            .fetchOne();
    }

    @Override
    public UserDto findUserByNickname(String nickname) {
        return queryFactory
            .select(userDto)
            .from(user)
            .where(user.nickname.eq(nickname))
            .fetchOne();
    }
}
