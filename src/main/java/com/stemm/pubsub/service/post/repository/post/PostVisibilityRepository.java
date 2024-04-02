package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.service.post.repository.dto.PostRepositoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostVisibilityRepository {
    /**
     * `PUBLIC` 게시물을 최신 순으로 조회합니다.
     */
    Page<PostRepositoryDto> findPublicPosts(Pageable pageable);

    // TODO: 다른 메서드 추가 예정
}
