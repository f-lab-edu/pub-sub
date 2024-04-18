package com.stemm.pubsub.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stemm.pubsub.service.auth.handler.LoginFailureHandler;
import com.stemm.pubsub.service.auth.handler.LoginSuccessHandler;
import com.stemm.pubsub.service.auth.json.JsonProcessingFilter;
import com.stemm.pubsub.service.auth.token.TokenProcessingFilter;
import com.stemm.pubsub.service.auth.token.TokenService;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService customUserDetailsService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(registry -> registry
                .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
                .requestMatchers("/h2-console/**", "/error").permitAll()
                .requestMatchers("/login", "/signup").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(STATELESS))
            .addFilterAfter(jsonProcessingFilter(), LogoutFilter.class)
            .addFilterBefore(tokenProcessingFilter(), JsonProcessingFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(tokenService, userRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public JsonProcessingFilter jsonProcessingFilter() {
        JsonProcessingFilter jsonProcessingFilter = new JsonProcessingFilter(objectMapper);
        jsonProcessingFilter.setAuthenticationManager(authenticationManager());
        jsonProcessingFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        jsonProcessingFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonProcessingFilter;
    }

    @Bean
    public TokenProcessingFilter tokenProcessingFilter() {
        return new TokenProcessingFilter(tokenService, customUserDetailsService);
    }
}
