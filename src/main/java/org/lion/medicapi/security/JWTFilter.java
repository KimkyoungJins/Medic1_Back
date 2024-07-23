package org.lion.medicapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더로부터 토큰 추출
        final String bearerToken = request.getHeader("Authorization");
        final String jwt = extractToken(bearerToken);
        log.debug("jwt[{}]", jwt);

        if (jwt != null) {
            // 인증 정보 추출
            final Authentication authentication = tokenProvider.getAuthentication(jwt);

            if (authentication != null) {
                // 컨텍스트에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(final String bearerToken) {

        if (StringUtils.hasText(bearerToken) &&
                (bearerToken.startsWith("Bearer ") || bearerToken.startsWith("bearer "))) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
