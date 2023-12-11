package oneulmwohaji.global.jwt.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.global.jwt.service.JwtProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("filter 입장");
        String jwt = retrieveToken(request);
        if (StringUtils.hasText(jwt) && jwtProvider.validateAccessToken(jwt)) {
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            jwtProvider.getAuthentication(jwt)
                    );
        } else {
            log.info("유효한 토큰이 없습니다.");
        }
        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
