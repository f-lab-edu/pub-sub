package com.stemm.pubsub.service.user.repository.user;

public interface UserInfoRepository {
    UserDto findUserById(Long userId);
    UserDto findUserByNickname(String nickname);
}
