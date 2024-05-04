package com.stemm.pubsub.service.post.repository.postlike;

import com.stemm.pubsub.service.post.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query(
        "select new com.stemm.pubsub.service.post.repository.postlike.PostLikeDto(" +
            "p.id, " +
            "sum(case when pl.status = 'LIKE' then 1 else 0 end), " +
            "sum(case when pl.status = 'DISLIKE' then 1 else 0 end)" +
        ") " +
        "from Post p " +
        "left join PostLike pl on p.id = pl.post.id " +
        "where p.id = :postId " +
        "group by p.id"
    )
    PostLikeDto findLikeAndDislikeCountByPostId(Long postId);
}
