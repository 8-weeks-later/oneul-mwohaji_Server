package oneulmwohaji.global.auth.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.global.auth.jwt.exception.TokenException;
import oneulmwohaji.global.auth.jwt.exception.TokenExpiredException;
import oneulmwohaji.global.auth.jwt.exception.TokenUnsupportedException;
import oneulmwohaji.global.auth.jwt.MemberPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final MemberRepository memberRepository;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.accessToken.expiration-period}")
    private int accesesTokenexpirationPeriod;
    @Value("${jwt.refreshToken.expiration-period}")
    private int refreshTokenexpirationPeriod;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createAccessToken(String OAuthId) {
        Claims claims = Jwts.claims();
        claims.put("oauthId", OAuthId);
        return createToken(claims, accesesTokenexpirationPeriod);
    }

    public String createRefreshToken(String OAuthId) {
        Claims claims = Jwts.claims();
        claims.put("oauthId", OAuthId);
        return createToken(claims, refreshTokenexpirationPeriod);
    }

    private String createToken(Claims claims, int expirationPeriod) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(expiredAt(expirationPeriod))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Member getMemberFromAuthentication(Authentication authentication) {
        return (Member) authentication.getPrincipal();
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date expiredAt(int expirationPeriod) {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusHours(expirationPeriod).atZone(ZoneId.systemDefault()).toInstant());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (UnsupportedJwtException e) {
            throw new TokenUnsupportedException();
        } catch (Exception e) {
            throw new TokenException();
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String oauthId = getMemberIdFromToken(token);
        Member member = memberRepository.findMemberByOauthId(oauthId).orElseThrow(() -> new MemberNotFoundException());
        MemberPrincipal memberPrincipal = new MemberPrincipal(member);
        return new UsernamePasswordAuthenticationToken(memberPrincipal, token, memberPrincipal.getAuthorities());
    }

    private String getMemberIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("oauthId",
                        String.class);
    }
}
