package org.uy.sdm.pasman.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uy.sdm.pasman.exceptions.CredentialNotFoundException;
import org.uy.sdm.pasman.util.crypto.EncryptionException;

@ControllerAdvice
public class AppExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleIUsernameNotFoundException(UsernameNotFoundException ex) {
		final String errorMessage = String.format("Username Not found: [%s].",ex.getMessage());
		LOGGER.error(errorMessage,ex);
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler(EncryptionException.class)
	public ResponseEntity<String> handleEncryptionException(EncryptionException ex) {
		final String errorMessage = String.format("An Encryption error occurred: [%s].",ex.getMessage());
		LOGGER.error(errorMessage,ex);
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler(CredentialNotFoundException.class)
	public ResponseEntity<String> handleCredentialNotFoundException(CredentialNotFoundException ex) {
		LOGGER.error(ex.getMessage(),ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<String> handleJacksonException(JsonProcessingException ex) {
		final String errorMessage = String.format("An exception occurred parsing json object: [%s].",ex.getMessage());
		LOGGER.error(errorMessage,ex);
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		final String errorMessage = String.format("Something went wrong: [%s]. Please try again later.",ex.getMessage());
		LOGGER.error(errorMessage,ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(String.format(errorMessage,ex.getMessage()));
	}

}
