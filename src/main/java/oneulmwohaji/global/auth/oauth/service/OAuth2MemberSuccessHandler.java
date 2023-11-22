package oneulmwohaji.global.auth.oauth.service;

import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.global.auth.jwt.service.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String oAuthId = getOAuthIdFromOAuth2User(oAuth2User);
        redirect(request, response, oAuthId);
    }

    private String getOAuthIdFromOAuth2User(OAuth2User oAuth2User) {
        return oAuth2User.getAttributes().get("id").toString();
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response,
                          String oAuthId) throws IOException {
        String accessToken = jwtProvider.createAccessToken(oAuthId);  // Access Token 생성
        String refreshToken = jwtProvider.createRefreshToken(oAuthId);   // Refresh Token 생성

        String uri = createURI(accessToken, refreshToken).toString(); // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    // Redirect URI 생성. JWT를 쿼리 파라미터로 담아 전달한다.
    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("example")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}