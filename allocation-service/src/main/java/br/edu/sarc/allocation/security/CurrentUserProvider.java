package br.edu.sarc.allocation.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public CurrentUser get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new CurrentUser("", false, false);
        }

        String email = authentication.getName();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            email = jwt.getClaimAsString("email") != null ? jwt.getClaimAsString("email") : jwt.getSubject();
        }

        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean professor = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_PROFESSOR"));

        return new CurrentUser(email, admin, professor);
    }
}
