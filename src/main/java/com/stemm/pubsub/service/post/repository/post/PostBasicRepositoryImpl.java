package com.stemm.pubsub.service.post.repository.post;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.post.entity.post.LikeStatus.DISLIKE;
import static com.stemm.pubsub.service.post.entity.post.LikeStatus.LIKE;
import static com.stemm.pubsub.service.post.entity.post.QPost.post;
import static com.stemm.pubsub.service.post.entity.post.QPostLike.postLike;

@RequiredArgsConstructor
public class PostBasicRepositoryImpl implements PostBasicRepository {

    private final JPAQueryFactory queryFactory;

    private final ConstructorExpression<PostDto> postDto = constructor(
        PostDto.class,
        post.id,
        post.user.id,
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
    public Optional<PostDto> findPostById(Long postId) {
        return Optional.ofNullable(
            queryFactory
                .select(postDto)
                .from(post)
                .leftJoin(postLike).on(post.id.eq(postLike.post.id))
                .where(post.id.eq(postId))
                .groupBy(post.id)
                .fetchOne()
        );
    }
}
