package org.lion.medicapi.util;

import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public final class AuthenticationUtils {

    private AuthenticationUtils() {
    }

    public static User getUser() throws AuthenticationException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            final Object principal = authentication.getPrincipal();

            if (principal instanceof User user) {
                log.debug("user[{}]", user);
                return user;
            }
        }

        throw new BadCredentialsException("Bad Credentials");
    }
}
