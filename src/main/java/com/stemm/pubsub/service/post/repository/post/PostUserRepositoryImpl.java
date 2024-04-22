package com.stemm.pubsub.service.post.repository.post;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.post.entity.post.Visibility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.post.entity.post.LikeStatus.DISLIKE;
import static com.stemm.pubsub.service.post.entity.post.LikeStatus.LIKE;
import static com.stemm.pubsub.service.post.entity.post.QPost.post;
import static com.stemm.pubsub.service.post.entity.post.QPostLike.postLike;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PRIVATE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static org.springframework.data.support.PageableExecutionUtils.getPage;

@RequiredArgsConstructor
public class PostUserRepositoryImpl implements PostUserRepository {

    private final JPAQueryFactory queryFactory;

    private final ConstructorExpression<PostDto> postDto = constructor(
        PostDto.class,
        post.id,
        post.user.nickname,
        post.user.profileImageUrl,
        post.content,
        post.imageUrl,
        post.visibility,
        postLike.status.when(LIKE).then(1).otherwise(0).sum().intValue().as("likeCount"),
        postLike.status.when(DISLIKE).then(1).otherwise(0).sum().intValue().as("dislikeCount"),
        post.createdDate,
        post.lastModifiedDate
    );

    @Override
    public Page<PostDto> findPublicPostsByUserId(Long userId, Pageable pageable) {
        return getPostDtos(userId, pageable, PUBLIC);
    }

    @Override
    public Page<PostDto> findPrivatePostsByUserId(Long userId, Pageable pageable) {
        return getPostDtos(userId, pageable, PRIVATE);
    }

    private Page<PostDto> getPostDtos(Long userId, Pageable pageable, Visibility visibility) {
        List<PostDto> content = queryFactory
            .select(postDto)
            .from(post)
            .leftJoin(postLike).on(post.id.eq(postLike.post.id))
            .where(userIdEquals(userId), visibilityEquals(visibility))
            .groupBy(post.id)
            .orderBy(post.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(userIdEquals(userId), visibilityEquals(visibility));

        return getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression userIdEquals(Long userId) {
        return post.user.id.eq(userId);
    }

    private BooleanExpression visibilityEquals(Visibility visibility) {
        return post.visibility.eq(visibility);
    }
}
