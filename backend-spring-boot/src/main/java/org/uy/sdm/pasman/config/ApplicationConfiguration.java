package org.uy.sdm.pasman.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.uy.sdm.pasman.security.AuthenticationFilter;
import org.uy.sdm.pasman.security.AuthorizationFilter;
import org.uy.sdm.pasman.security.JwtManager;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationConfiguration implements WebMvcConfigurer {

	private final ApplicationProperties applicationProperties;

	private static final String[] noAuthRequiredEndpoints = {
		// login.
		"/login/**",
		// logout.
		"/logout/**",
		// Create User.
		"/users/create/**",
		// auth.
		"/users/isAuthenticated/**",
	};

	@Bean
	public AuthenticationManager authenticationManager(
		final HttpSecurity http,
		final UserDetailsService userDetailsService) throws Exception {
		return http.userDetailsService(userDetailsService)
			.getSharedObject(AuthenticationManager.class);
	}

	@Bean
	public SecurityFilterChain filterChain (
		final HttpSecurity httpSecurity,
		final AuthorizationFilter authorizationFilter,
		final AuthenticationFilter authenticationFilter
	) throws Exception {
		httpSecurity.headers(headers ->
			headers.httpStrictTransportSecurity(
				HeadersConfigurer.HstsConfig::disable));
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.headers(headers -> headers.addHeaderWriter(
				new ContentSecurityPolicyHeaderWriter(applicationProperties.getAllowedOrigins())
			)).cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> request.
				requestMatchers(noAuthRequiredEndpoints)
				.permitAll().anyRequest().authenticated()
			)
			.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity
			.sessionManagement(session -> session.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationFilter))
			.logout(logout -> logout.invalidateHttpSession(true)
				.deleteCookies(JwtManager.SEC_JWT_COOKIE)
				.clearAuthentication(true)
				.logoutSuccessHandler(authenticationFilter).permitAll())
			.formLogin((formLogin -> {
				formLogin.successHandler(authenticationFilter);
				formLogin.failureHandler(authenticationFilter);
			}));
		return httpSecurity.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthorizationFilter authorizationFilter(JwtManager jwtManager){
		return new AuthorizationFilter(jwtManager);
	}

	@Bean
	AuthenticationFilter authenticationFilter(
		JwtManager jwtManager,
		ApplicationProperties applicationProperties
	){
		return new AuthenticationFilter(jwtManager,applicationProperties);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins(applicationProperties.getAllowedOrigins())
			.allowCredentials(true)
			.allowedMethods("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE");
	}
}
