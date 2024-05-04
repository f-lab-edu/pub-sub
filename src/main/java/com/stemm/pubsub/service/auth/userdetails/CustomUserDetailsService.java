package com.stemm.pubsub.service.auth.userdetails;

import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new UsernameNotFoundException("해당" + nickname + " 유저가 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }
}
