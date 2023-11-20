package oneulmwohaji.global.auth.oauth.service;

import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.global.auth.jwt.service.JwtProvider;
import oneulmwohaji.global.auth.oauth.entity.OAuth2CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        redirect(request, response, authentication);  // Access Token과 Refresh Token을 Frontend에 전달하기 위해 Redirect
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException {
        String accessToken = jwtProvider.createAccessToken(authentication);  // Access Token 생성
        String refreshToken = jwtProvider.createRefreshToken(authentication);     // Refresh Token 생성

        String uri = createURI(accessToken, refreshToken).toString();   // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response,
                uri);   // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
    }

    // Redirect URI 생성. JWT를 쿼리 파라미터로 담아 전달한다.
    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("munny")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}