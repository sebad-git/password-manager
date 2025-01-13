package org.uy.sdm.pasman.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.uy.sdm.pasman.model.SecurityUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtManager {

	@Value("${jwt.secretKey}")
	private String jwtSecretKey;

	private static final long JWT_TOKEN_LIFETIME_IN_MS = 7200L;
	public static final long JWT_TOKEN_LIFETIME_IN_SEC = JWT_TOKEN_LIFETIME_IN_MS * 1000L;

	public static final String SEC_JWT_COOKIE = "pm-jwt";

	public static final String USER_KEY = "usr";
	public static final String SESSION_KEY = "session";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtManager.class);

	private static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

	private String getSigningKey() {
		return TextCodec.BASE64URL.encode(jwtSecretKey);
	}

	/**
	 * Generate a JWT string based on the given {@link UserDetails}.
	 * @param userDetails {@link UserDetails}
	 * @return JWT string
	 */
	public String createTokenFromUser(UserDetails userDetails) {
		if (userDetails == null) {
			throw new IllegalArgumentException();
		}
		final Map<String, Object> claims = generateClaims((SecurityUser) userDetails);

		final Date issuedTime = Date.from(
			LocalDateTime.now()
				.atZone(SYSTEM_ZONE).toInstant()
		);
		final Date expirationDate = Date.from(LocalDateTime.now().
			plusMinutes(JWT_TOKEN_LIFETIME_IN_MS).
			atZone(SYSTEM_ZONE).toInstant()
		);

		return Jwts
			.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(issuedTime)
			.setExpiration(expirationDate)
			.signWith(SIGNATURE_ALGORITHM, getSigningKey())
			.compact();
	}

	private Map<String, Object> generateClaims(SecurityUser userDetails) {
		SecurityUser securityUserDetails = new SecurityUser();
		securityUserDetails.setUsername(userDetails.getUsername());
		securityUserDetails.setPassword(userDetails.getPassword());
		securityUserDetails.setAccountNonExpired(userDetails.isAccountNonExpired());
		securityUserDetails.setAccountNonLocked(userDetails.isAccountNonLocked());
		securityUserDetails.setCredentialsNonExpired(userDetails.isCredentialsNonExpired());
		securityUserDetails.setEnabled(userDetails.isEnabled());
		Map<String, Object> claims = new HashMap<>();
		claims.put(USER_KEY, securityUserDetails);
		claims.put(SESSION_KEY, UUID.randomUUID().toString());
		return claims;
	}

	/**
	 * Checks the given string is a valid JWT.
	 * @param jwt JWT string
	 * @return Is valid.
	 * @throws JwtException if invalid jwt
	 */
	public boolean isValid(String jwt) {
		try {
			return isSigned(jwt) && !isExpired(jwt);
		} catch (IllegalArgumentException | ExpiredJwtException e) {
			LOGGER.info("Error validating token. Error:{}",e.getMessage());
			return false;
		}
	}

	private boolean isExpired(String jwt) {
		return getClaim(jwt, Claims::getExpiration).before(new Date());
	}
	private boolean isSigned(String jwt) {
		return Jwts.parser().isSigned(jwt);
	}

	/**
	 * Retrieve a claim from the given JWT.
	 * @param jwt string
	 * @param claimsResolver claim method
	 * @param <T> claim data type
	 * @return claim value
	 */
	private <T> T getClaim(String jwt, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(
			Jwts.parser().setSigningKey(this.getSigningKey()).parseClaimsJws(jwt).getBody()
		);
	}

	/**
	 * Creates a Jwt token for an authenticated user.
	 * @param userName an authenticated user.
	 * @return a Jwt token.
	 */
	public final String createToken(final String userName) {

		final Date issuedTime = Date.from(
			LocalDateTime.now(SYSTEM_ZONE)
				.atZone(SYSTEM_ZONE).toInstant()
		);
		final Date expirationDate = Date.from(LocalDateTime.now().
			plusMinutes(JWT_TOKEN_LIFETIME_IN_MS).
			atZone(SYSTEM_ZONE).toInstant()
		);
		return Jwts.builder()
			.setSubject(userName)
			.setIssuedAt(issuedTime)
			.setExpiration(expirationDate)
			.signWith(SIGNATURE_ALGORITHM,getSigningKey())
			.compact();
	}

	/** Creates a User and Password authentication object.
	 * @see UsernamePasswordAuthenticationToken
	 * @param token a jwt token.
	 * @return User and Password authentication object.
	 */
	public final UsernamePasswordAuthenticationToken getAuthentication(final String token){
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(getSigningKey())
				.parseClaimsJws(token.trim())
				.getBody();
			return new UsernamePasswordAuthenticationToken(
				claims.getSubject(),token, Collections.emptyList()
			);
		}catch(JwtException e){
			LOGGER.error("token invalid or login expired",e);
			return null;
		}
	}

	/**
	 * Validates that the token exists, is signed and that it's not expired.
	 * @param cookie value containing the token.
	 * @return If the token is valid.
	 */
	public final boolean tokenIsValid(final String cookie){
		return StringUtils.isNotEmpty(cookie)
			&& this.isValid(cookie);
	}

	/**
	 * Removes the format: se-jwt=eyToKen.vAuE and get the token value: eyToKen.vAuE.
	 * @param cookieHeader header value containing the token.
	 * @return The token parsed.
	 */
	public final String parseCookieToken(final String cookieHeader){
		if(StringUtils.isEmpty(cookieHeader))
			return null;
		return cookieHeader.replace(
			String.format("%s=", SEC_JWT_COOKIE),
			StringUtils.EMPTY
		);
	}

	/**
	 * Get the username (sub) from the given JWT.
	 * @param jwt string
	 * @return username or null if none found
	 */
	public final String getUsername(final String jwt) {
		try {
			return this.getClaim(jwt, Claims::getSubject);
		} catch (IllegalArgumentException | MalformedJwtException e) {
			LOGGER.info("An error occurred getting username from token. Error:{}",e.getMessage());
			return null;
		}
	}

	/**
	 * Gets the token from the request.
	 * If not found in the cookie list check the cookie header.
	 * @param request The user request.
	 * @return The jwt token.
	 */
	public String getAuthCookie(@NonNull HttpServletRequest request){
		final Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			final String authCookie = Arrays.stream(cookies)
				.filter(cookie -> SEC_JWT_COOKIE.equals(cookie.getName()))
				.map(Cookie::getValue)
				.findFirst().orElse(null);
			if (StringUtils.isNotEmpty(authCookie))
				return authCookie;
		}
		return getAuthCookieFromHeader(request);
	}

	/**
	 * Splits the header by semicolon and looks for the cookie with se-jwt name.
	 * Eg: se-jwt=eyToken.abc; Other=someToken.
	 * If not found returns the first one found.
	 * If no semicolon is found returns the header.
	 * @param request The user request.
	 * @return The jwt token.
	 */
	protected String getAuthCookieFromHeader(@NonNull HttpServletRequest request){
		final String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
		if(cookieHeader==null)
			return null;
		if(!cookieHeader.contains(";")) {
			final String[] headerCookies = cookieHeader.split(";");
			final String authCookie = Arrays.stream(headerCookies)
				.filter(SEC_JWT_COOKIE::contains)
				.findFirst().orElse(null);
			if(!StringUtils.isNotEmpty(authCookie))
				return this.parseCookieToken(headerCookies[0]);
		}
		return this.parseCookieToken(cookieHeader);
	}

}
