package oneulmwohaji.global.config;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.global.auth.jwt.service.JwtProvider;
import oneulmwohaji.global.auth.oauth.service.CustomOAuth2UserService;
import oneulmwohaji.global.auth.oauth.handler.OAuth2MemberSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtProvider jwtProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .successHandler(new OAuth2MemberSuccessHandler(jwtProvider))
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
