package org.uy.sdm.pasman.config;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Validated
public class ApplicationProperties {

	@Value("${allowedOrigins}")
	@Valid
	private String allowedOrigins;

	@Value("${server.servlet.context-path}")
	@Valid
	private String contextPath;

	@Valid
	@Value("${jwt.secretKey: insecure}")
	private String secretKey;

}
