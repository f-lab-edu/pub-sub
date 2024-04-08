package com.stemm.pubsub.service.post.repository.comment;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.stemm.pubsub.service.post.entity.QComment.comment;
import static org.springframework.data.support.PageableExecutionUtils.getPage;

@RequiredArgsConstructor
public class CommentPostRepositoryImpl implements CommentPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentDto> findCommentsForPost(Long postId, Pageable pageable) {
        List<CommentDto> content = queryFactory
            .select(
                constructor(
                    CommentDto.class,
                    comment.id,
                    comment.user.nickname,
                    comment.user.profileImageUrl,
                    comment.content,
                    comment.likeCount,
                    comment.dislikeCount,
                    comment.createdDate,
                    comment.lastModifiedDate
                )
            )
            .from(comment)
            .where(comment.post.id.eq(postId))
            .orderBy(comment.likeCount.desc(), comment.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(comment.count())
            .from(comment)
            .where(comment.post.id.eq(postId));

        return getPage(content, pageable, countQuery::fetchOne);
    }
}
