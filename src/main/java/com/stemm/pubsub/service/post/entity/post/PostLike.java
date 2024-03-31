package com.stemm.pubsub.service.post.entity.post;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.service.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PostLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(STRING)
    @Column(nullable = false, length = 10)
    private LikeStatus status;

    public PostLike(Post post, User user, LikeStatus status) {
        this.post = post;
        this.user = user;
        this.status = status;
    }
}
