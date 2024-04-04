package com.stemm.pubsub.service.user.repository.user;

public interface UserInfoRepository {
    UserDto findByUserId(Long userId);
    UserDto findByNickname(String nickname);
}
