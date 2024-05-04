package com.stemm.pubsub.service.post.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLike extends EntityPathBase<PostLike> {

    private static final long serialVersionUID = -1454600005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final com.stemm.pubsub.common.QBaseEntity _super = new com.stemm.pubsub.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QPost post;

    public final EnumPath<LikeStatus> status = createEnum("status", LikeStatus.class);

    public final com.stemm.pubsub.service.user.entity.QUser user;

    public QPostLike(String variable) {
        this(PostLike.class, forVariable(variable), INITS);
    }

    public QPostLike(Path<? extends PostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLike(PathMetadata metadata, PathInits inits) {
        this(PostLike.class, metadata, inits);
    }

    public QPostLike(Class<? extends PostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.stemm.pubsub.service.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

