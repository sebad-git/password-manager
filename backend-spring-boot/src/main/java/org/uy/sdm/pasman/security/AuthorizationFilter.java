package org.uy.sdm.pasman.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.uy.sdm.pasman.config.ApplicationProperties;

import java.io.IOException;

@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        try {
            final String token = jwtManager.getAuthCookie(request);
            if(token==null || !jwtManager.tokenIsValid(token)) {
                final SecurityContext context = SecurityContextHolder.createEmptyContext();
                SecurityContextHolder.setContext(context);
                request.getSession().invalidate();
                LOGGER.error("Token invalid during authentication.");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                filterChain.doFilter(request, response);
                return;
            }
            final UsernamePasswordAuthenticationToken authenticationToken =
				jwtManager.getAuthentication(token);
            final SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("An error occurred during authentication. Error:{}",e.getMessage());
        }
    }

}
