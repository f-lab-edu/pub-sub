package com.stemm.pubsub.service.user.entity;

import com.stemm.pubsub.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "membership_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int monthlyPrice;

    public Membership(String name, int monthlyPrice) {
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }
}
