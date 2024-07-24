package org.lion.medicapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class TokenProvider {
    @Value("${jwt.access.secret-key}")
    private String secretKey;

    @Value("${jwt.access.expired-milly-seconds}")
    private Long expiredMillySeconds;

    /**
     * JWT 생성 메서드
     */
    public String createAccessToken(final User user) {

        final Date now = new Date();
        final Date validity = new Date(now.getTime() + expiredMillySeconds);

        final Map<String, Object> claim = new HashMap<>();
        claim.put("userId", user.getId());

        String subject = user.getEmail();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject).setIssuedAt(now)
                .addClaims(claim)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * JWT로부터 의료진 정보(claim) 추출 메서드
     *
     * @param token : JWT
     * @return : 의료진 정보(claim)
     */
    public Authentication getAuthentication(final String token) {

        final Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        final String email = body.getSubject();
        final Long userId = ((Number) (body.get("userId"))).longValue();

        final User user = User.builder()
                .id(userId)
                .email(email)
                .build();

        return new UsernamePasswordAuthenticationToken(user, "",
                AuthorityUtils.createAuthorityList());
    }
}
