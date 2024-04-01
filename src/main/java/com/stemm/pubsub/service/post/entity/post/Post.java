package com.stemm.pubsub.service.post.entity.post;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.service.post.entity.Comment;
import com.stemm.pubsub.service.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(indexes = @Index(name = "idx_post_created_date", columnList = "created_date"))
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @Enumerated(STRING)
    @Column(nullable = false, length = 10)
    private Visibility visibility;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(User user, String content, String imageUrl, Visibility visibility) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.visibility = visibility;
    }

    public void addLike(PostLike postLike) {
        likes.add(postLike);

        if (postLike.getPost() != this) {
            postLike.setPost(this);
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);

        if (comment.getPost() != this) {
            comment.setPost(this);
        }
    }
}
