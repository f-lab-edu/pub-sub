package com.stemm.pubsub.service.post.entity;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(indexes = @Index(name = "idx_comment_created_date", columnList = "created_date"))
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Setter
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
    private int likeCount;

    @Column(nullable = false)
    private int dislikeCount;

    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    public void incrementLikeCount() {
        likeCount++;
    }

    public void decrementLikeCount() {
        if (likeCount > 0) {
            likeCount--;
        }
    }

    public void incrementDislikeCount() {
        dislikeCount++;
    }

    public void decrementDislikeCount() {
        if (dislikeCount > 0) {
            dislikeCount--;
        }
    }
}
