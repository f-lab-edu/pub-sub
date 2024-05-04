package com.stemm.pubsub.service.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMembership is a Querydsl query type for Membership
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMembership extends EntityPathBase<Membership> {

    private static final long serialVersionUID = -1258185877L;

    public static final QMembership membership = new QMembership("membership");

    public final com.stemm.pubsub.common.QBaseEntity _super = new com.stemm.pubsub.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> monthlyPrice = createNumber("monthlyPrice", Integer.class);

    public final StringPath name = createString("name");

    public QMembership(String variable) {
        super(Membership.class, forVariable(variable));
    }

    public QMembership(Path<? extends Membership> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMembership(PathMetadata metadata) {
        super(Membership.class, metadata);
    }

}

