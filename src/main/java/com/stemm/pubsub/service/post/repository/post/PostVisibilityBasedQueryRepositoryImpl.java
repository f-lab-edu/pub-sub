package com.stemm.pubsub.service.post.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stemm.pubsub.service.post.dto.response.PostResponseDto;
import com.stemm.pubsub.service.post.entity.post.Visibility;
import jakarta.persistence.EntityManager;
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

public class PostVisibilityBasedQueryRepositoryImpl implements PostVisibilityBasedQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostVisibilityBasedQueryRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<PostResponseDto> findPublicPosts(Pageable pageable) {
        return getPostResponseDtos(pageable, PUBLIC);
    }

    @Override
    public Page<PostResponseDto> findPrivatePosts(Pageable pageable) {
        return getPostResponseDtos(pageable, PRIVATE);
    }

    private Page<PostResponseDto> getPostResponseDtos(Pageable pageable, Visibility visibility) {
        List<PostResponseDto> content = queryFactory
            .select(
                constructor(
                    PostResponseDto.class,
                    post.id,
                    post.user.nickname,
                    post.content,
                    post.imageUrl,
                    post.createdDate,
                    post.lastModifiedDate,
                    postLike.status.when(LIKE).then(1).otherwise(0).sum().intValue().as("likeCount"),
                    postLike.status.when(DISLIKE).then(1).otherwise(0).sum().intValue().as("dislikeCount")
                )
            )
            .from(post)
            .leftJoin(post.likes, postLike)
            .where(isVisibilityEquals(visibility))
            .groupBy(post.id)
            .orderBy(post.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(isVisibilityEquals(visibility));

        return getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression isVisibilityEquals(Visibility visibility) {
        return post.visibility.eq(visibility);
    }
}
