package com.stemm.pubsub.service.post.entity;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.service.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@Table(indexes = @Index(name = "idx_comment_created_date", columnList = "created_date"))
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long likeCount;

    public Comment(Post post, User user, String content, Long likeCount) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.likeCount = likeCount;
    }
}
