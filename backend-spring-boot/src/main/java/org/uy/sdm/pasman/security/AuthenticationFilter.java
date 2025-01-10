package org.uy.sdm.pasman.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.uy.sdm.pasman.config.ApplicationProperties;
import org.uy.sdm.pasman.model.SecurityUser;
import org.uy.sdm.pasman.util.jackson.Jackson;

import java.io.IOException;
import java.rmi.ServerException;

/**
 * Filter on login/logout.
 */
@AllArgsConstructor
public class AuthenticationFilter implements
        AuthenticationEntryPoint,
        AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        LogoutSuccessHandler {

    private JwtManager jwtManager;
	private ApplicationProperties applicationProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final ResponseEntity<Void> API_RESPONSE_NOT_IMPLEMENTED;
    private static final ResponseEntity<Void> API_RESPONSE_UNAUTHORIZED;
    private static final ObjectWriter OBJECT_WRITER;

    static {
        OBJECT_WRITER =
                Jackson.getObjectMapper().writerFor(new TypeReference<ResponseEntity<Void>>() {});
        API_RESPONSE_NOT_IMPLEMENTED = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        API_RESPONSE_UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * After an authentication is successful it creates a jwt token.
     * @param request the request data.
     * @param response the response data.
     * @param authResult the authentication result.
     * @throws ServerException a ServerException
     */
    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authResult
    ) throws ServerException {
        if (authResult instanceof UsernamePasswordAuthenticationToken) {
            this.handleUserPasswordAuthentication(response,authResult);
            return;
        }
        writeStatus(response, HttpStatus.NOT_IMPLEMENTED);
    }

    private void handleUserPasswordAuthentication(
            final HttpServletResponse response,
            final Authentication authResult) throws ServerException {
        SecurityUser userDetails = (SecurityUser)authResult.getPrincipal();
        this.handleInternalAuthentication(response,userDetails);
    }

    private void handleInternalAuthentication(
            final HttpServletResponse response,
            final SecurityUser userDetails) throws ServerException {
        if(userDetails!=null) {
            final String tokenUser;
            try {
                tokenUser = Jackson.getObjectMapper().writeValueAsString(userDetails);
                final String token = jwtManager.createToken(tokenUser);
                this.createCookie(response,token);
                SecurityContextHolder.getContext().setAuthentication(
					jwtManager.getAuthentication(token)
                );
                writeStatus(response,HttpStatus.OK);
            } catch (JsonProcessingException e) {
                LOGGER.error("An error occurred parsing security user:{}",e.getMessage());
                writeStatus(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            writeStatus(response,HttpStatus.UNAUTHORIZED);
        }
    }

    private void createCookie(
            final HttpServletResponse response,
            final String token) {
        final long cookieAge = JwtManager.JWT_TOKEN_LIFETIME_IN_SEC;
        final ResponseCookie jwtCookie = ResponseCookie.from(JwtManager.SEC_JWT_COOKIE, token)
                .httpOnly(true)
                .secure(true)
                .path(applicationProperties.getContextPath())
                .maxAge(cookieAge)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        String username = request.getParameter("username");
        LOGGER.error("username: {}",username,exception);
        writeStatus(response, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        // if in this method, auth via jwt failed
        writeStatus(response, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        LOGGER.info("User logged out.");
    }

    private void writeStatus(
            final HttpServletResponse response, final HttpStatus status
    ) throws ServerException {
        try {
            // set HTTP header
            response.setStatus(status.value());
            // set HTTP response body
            ResponseEntity<Void> apiResponse;
            if (status == HttpStatus.UNAUTHORIZED)
                apiResponse = API_RESPONSE_UNAUTHORIZED;
            else if (status == HttpStatus.NOT_IMPLEMENTED)
                apiResponse = API_RESPONSE_NOT_IMPLEMENTED;
            else apiResponse = toApiResponse(status);
            OBJECT_WRITER.writeValue(response.getOutputStream(), apiResponse);
        } catch (IOException ex){
            throw new ServerException(ex.getMessage(),ex);
        }
    }

    private static ResponseEntity<Void> toApiResponse(final HttpStatus status) {
		return ResponseEntity.status(status).build();
    }
}
