package com.stemm.pubsub.service.user.repository.user;

import com.stemm.pubsub.service.user.repository.dto.UserRepositoryDto;

public interface UserQueryRepository {
    UserRepositoryDto findUserById(Long userId);
    UserRepositoryDto findUserByNickname(String nickname);
}
