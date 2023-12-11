package oneulmwohaji.global.auth.controller;

import static oneulmwohaji.global.oauth.ConstantValue.ACCESS_TOKEN;
import static oneulmwohaji.global.oauth.ConstantValue.REFRESH_TOKEN;

import java.util.Optional;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.global.auth.exception.MemberSigninException;
import oneulmwohaji.global.auth.dto.request.MemberOAuthRequestDto;
import oneulmwohaji.global.auth.dto.request.MemberRegistRequestDto;
import oneulmwohaji.global.auth.service.AuthService;
import oneulmwohaji.global.jwt.service.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    @PostMapping("/auth/member")
    public ResponseEntity<?> registMember(@RequestBody @Validated MemberOAuthRequestDto memberOAuthRequestDto) {
        String oauthId = memberOAuthRequestDto.getOauthId();
        Optional<Member> memberOptional = authService.findMemberByOAuthId(oauthId);
        if (memberOptional.isEmpty()) {
            throw new MemberSigninException();
        }
        String accessToken = jwtProvider.createAccessToken(oauthId);
        String refreshToken = jwtProvider.createRefreshToken(oauthId);

        Cookie cookie = createHttpOnlyCookie(refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
        httpHeaders.add(ACCESS_TOKEN, accessToken);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/auth/additional/member")
    public ResponseEntity<?> AdditionalRegistMember(
            @RequestBody @Validated MemberRegistRequestDto memberRegistRequestDto) {
        authService.registMember(memberRegistRequestDto);

        return ResponseEntity.ok(memberRegistRequestDto);
    }

    private Cookie createHttpOnlyCookie(String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
