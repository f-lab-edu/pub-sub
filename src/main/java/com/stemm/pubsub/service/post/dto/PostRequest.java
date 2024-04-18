package com.stemm.pubsub.service.post.dto;

import com.stemm.pubsub.service.post.entity.post.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    String content,

    String imageUrl,

    @NotNull(message = "게시물의 공개 범위는 PUBLIC 또는 PRIVATE 이어야 합니다.")
    Visibility visibility
) {}
