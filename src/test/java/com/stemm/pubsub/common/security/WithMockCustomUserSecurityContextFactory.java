package com.stemm.pubsub.common.security;

import com.stemm.pubsub.service.auth.userdetails.CustomUserDetails;
import com.stemm.pubsub.service.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static com.stemm.pubsub.service.user.entity.Role.USER;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
            .id(1L)
            .nickname(customUser.nickname())
            .name(customUser.username())
            .email(customUser.email())
            .password(customUser.password())
            .role(USER)
            .build();

        CustomUserDetails principal = new CustomUserDetails(user);

        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(
            principal,
            null,
            principal.getAuthorities()
        );

        context.setAuthentication(auth);

        return context;
    }
}
