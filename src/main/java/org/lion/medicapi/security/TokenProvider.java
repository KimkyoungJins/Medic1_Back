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
    public String createAccessToken(final User user, final String username) {
        log.debug("user[{}], username[{}]", user, username);

        final Date now = new Date();
        final Date validity = new Date(now.getTime() + expiredMillySeconds);

        final Map<String, Object> claim = new HashMap<>();
//        claim.put(USER_ID, user.getId());
//        claim.put(AUTHORITIES, user.getAuthorities());
//        claim.put(NAME, user.getName());
//        claim.put(COUNTRY_CODE, user.getCountryCode());

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username).setIssuedAt(now)
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

        final String username = body.getSubject();
//        final long userId = ((Number) (body.get(USER_ID))).longValue();
//        final String name = (String) body.get(NAME);
//        log.debug("username[{}], userId[{}], name[{}]", username, userId, name);

//        final List<Map<String, Object>> authorities = (List<Map<String, Object>>) body.get(AUTHORITIES);
//        final CountryCode countryCode = CountryCode.valueOf(body.get(COUNTRY_CODE) == null
//                ? COUNTRY_CODE_KOR
//                : (String) body.get(COUNTRY_CODE));

//        final Doctor doctor = Doctor.builder()
//                .id(userId)
//                .username(username)
//                .name(name)
//                .countryCode(countryCode)
//                .build();
        // TODO : CREATE USER

        return new UsernamePasswordAuthenticationToken(new User(), "",
                AuthorityUtils.createAuthorityList());
    }
}
