package com.example.sport_forever.common.config;

import com.example.sport_forever.common.enums.PermissionEnum;
import com.example.sport_forever.common.filter.JwtFilter;
import com.example.sport_forever.common.filter.QueryStringFilter;
import com.example.sport_forever.common.repository.TokenRepository;
import com.example.sport_forever.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret_key}")
    private String secretKey;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/login", "/auth/signup").permitAll();
                    requests.requestMatchers(HttpMethod.GET,"/api","/swagger-ui/*","/favicon.ico","/api/swagger-config", "/api/logistics").permitAll();
                    requests.requestMatchers("/auth/token").permitAll();
                    requests.requestMatchers("/admin/**").hasAnyRole(PermissionEnum.ADMIN.toString());
                    requests.requestMatchers("/ware/**").authenticated();
                    requests.requestMatchers("/user/**").authenticated();
                    requests.requestMatchers("/facility/**").authenticated();
                })
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtFilter(secretKey, userRepository, tokenRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new QueryStringFilter(), JwtFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
